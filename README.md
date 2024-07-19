# Gateway

Este é responsável por administrar os endpoints dos serviços.

Caso não deseje executar o [projeto completo](https://github.com/DFaccio/ecommerce-system), é interessante que ao menos tenha o [registro e descoberta de serviços](https://github.com/DFaccio/ecommerce-registry/tree/main).

## Como configurar

A configuração abaixo considera execução local. Caso deseje executar o projeto por completo, sugiro o README.md do [ecommerce](https://github.com/DFaccio/ecommerce-system).

Crie um arquivo .env no diretório. Neste adicione as chave abaixo.

```
EUREKA_SERVER=http://localhost:7070/eureka

PRODUCT_ADDRESS=lb://inventory
PAYMENT_ADDRESS=lb://payment
CART_ADDRESS=lb://cart-service
USER_ADDRESS=lb://ecommerce-user

JWT_SERVER=localhost:7073
```
### Informações sobre a variáveis de ambiente

#### _EUREKA_SERVER_

Para valor da variável EUREKA_SERVER, segue duas possibilidades:
1. Ambos executando em localhost, logo EUREKA_SERVER=http://localhost:7070/eureka.
2. Ambos executando em container, logo EUREKA_SERVER = http://server-discovery:7070/eureka
3. Executando o servidor em localhost e o gateway em container. Neste caso, ao subir o servidor, na página inicial em
   _Instace Info_ configure EUREKA_SERVER de acordo com o ipAddr apresentando. Exemplo: http://${ipAddr}:7070/eureka.
   Também será necessário remover as configurações de network e adicionar a configuração abaixo.

        network_mode: host
4. Caso apenas o serviço de descoberta esteja em container, basta conectar em http://localhost:7070/eureka.


      OBS.: Ao executar este em container, ele tentará se conectar a uma rede criada na aplicação de descoberta de serviços. Portanto, talvez seja melhor executar o projeto completo e parar os container que não deseje utilizar ou deseje executar         localmente.

#### _PRODUCT_ADDRESS, PAYMENT_ADDRESS, CART_ADDRESS e USER_ADDRESS_

Estas também podem assumir valores diferentes se o serviço de descoberta e o gateway tiver em container e algum serviço local. Novamente, talvez seja interessante olhar o projeto completo, mas segue um resumo.

O valor _lb_ refere-se a _load balancing_ e será geranciado através do Eureka Server. A string posterior a isso refere-se ao valor definido em application.properties de cada serviço na chave *spring.application.name*.

Tais valores não devem ser alterados em caso de execução no container ou execução local. No entanto, caso o Gateway esteja em container em algum serviço será executado localmente para teste/dev é necessário alterar o valor para _http://host.docker.internal:${server.port}_. Por exemplo, o serviço de produtos sterá desenvolvimento, logos os outros serviços permancem como o mesmo valor, porém em PRODUCT_ADDRESS ficará conforme abaixo.

    http://host.docker.internal:7072

#### JWT_SERVER

Devido o Spring Security e o sistema de autorização/autenticação, o Gateway faz validação se a rota precisa ser autorizada, está autorizada e para onde deve ser redirecionada. 

Está variável armazena o valor do serviço responsável por conter a chave pública que gera o token JWT. Assim, quando houver alguma requisição, o Gateway chama o serviço mencionado e valida o token.

Desta forma, ela pode assumir três valores.

* Gateway local e serviço local: localhost:7073
* Gateway container e serviço local: host.docker.internal:7073
* Gateway container e serviço container: user-service

    Obs.: Não realizei testes com o serviço no container e o gateway local, porém essa seria outra possibilidade.

## Acessando os serviços

Considerando o uso do container, a acesso aos serviços estara bloqueado, ou seja, todas as requisições devem ser realizadas através do Gateway.

A partir do registro no [Registro e descoberta de serviços](https://github.com/DFaccio/ecommerce-registry/tree/main) 
todos acessos aos métodos será feito a partir do gateway.

Portanto, para testes, considerando que está sendo executando localmente, isso inclui containes locais, seria:

http://localhost:7071/${server.servlet.context-path}/${path_adicionado_gateway}/${resto_da_url_definida_no_request_mapping}

Resultado: http://localhost:7071/ecommerce/inventory/api/v1/product

Obs.: Caso não esteja em container, o acesso aos serviços será possível através de suas respectivas portas.
