# Gateway

Este é responsável por administrar os endpoints dos serviços.

Caso não deseje executar o [projeto completo](https://github.com/DFaccio/ecommerce-system), é interessante que ao menos tenha o [registro e descoberta de serviços](https://github.com/DFaccio/ecommerce-registry/tree/main).

## Como configurar

Crie um arquivo .env no diretório. Neste adicione as chave abaixo.

    EUREKA_SERVER=enderço_do_servidor

    PRODUCT_ADDRESS=lb://spring-batch-products
    PAYMENT_ADDRESS=lb://payment
    CART_ADDRESS=lb://cart-service

Importante, caso não irá utilizar o projeto completo, não precisaria alterar nada. Caso, irá realizar algum desenvolvimento
em um dos serviços, verifique a explicação do [README.md do projeto completo](https://github.com/DFaccio/ecommerce-system), 
pois o endereço do gateway pode alterar caso esteja sendo executado em container ou parte dos serviços esteja sendo 
executado em container.

Para valor da variável EUREKA_SERVER, segue duas possibilidades:
1. Ambos executando em localhost, logo EUREKA_SERVER=http://localhost:7070/eureka.
2. Ambos executando em container, logo EUREKA_SERVER = http://server-discovery:7070/eureka
3. Executando o servidor em localhost e o gateway em container. Neste caso, ao subir o servidor, na página inicial em
   _Instace Info_ configure EUREKA_SERVER de acordo com o ipAddr apresentando. Exemplo: http://${ipAddr}:7070/eureka.
   Também será necessário remover as configurações de network e adicionar a configuração abaixo.

        network_mode: host
4. Caso apenas o serviço de descoberta esteja em container, basta conectar em http://localhost:7070/eureka.


      OBS.: Ao executar este em container, ele tentará se conectar a uma rede criada na aplicação de descoberta de serviços.

## Acessando os serviços

A partir do registro no [Registro e descoberta de serviços](https://github.com/DFaccio/ecommerce-registry/tree/main) 
todos acessos aos métodos será feito a partir do gateway.

      OBS.: Isto se o acesso aos outros serviços estiverem bloqueados. Do contrário, será possível acessar pelo serviço e pelo gateway.

Portanto, para testes, considerando que está sendo executando localmente, isso inclui containes locais, seria:

http://localhost:7071/${server.servlet.context-path}/${path_adicionado_gateway}/${resto_da_url_definida_no_request_mapping}
Resultado: http://localhost:7071/ecommerce/inventory/api/v1/product

Obs.: Caso não seja adicionado path, também é possível chamar passando: ${endereço do gateway}/${nome_serviço}/${resto_url_api} 
