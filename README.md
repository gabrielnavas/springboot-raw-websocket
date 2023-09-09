## Projeto Spring Boot 3 com WebSocket

Este é um projeto simples que demonstra a implementação de um servidor WebSocket usando Spring Boot 3, Java 17 e a  biblioteca Jackson Databind para processamento de JSON. O servidor WebSocket recebe uma mensagem JSON em formato de string, converte-a em um objeto e retorna a mensagem como uma string JSON.

#### Requisitos
##### Antes de começar, verifique se você atende aos seguintes requisitos:

1. Java 17: Certifique-se de que o Java Development Kit (JDK) 17 esteja instalado e configurado corretamente.
2. Qualquer IDE.

#### Como Executar o Projeto

1. Clone este repositório em sua máquina:
```shell  
git clone https://github.com/seu-usuario/projeto-websocket.git
```  

2. Navegue até o diretório do projeto:
```shell  
cd projeto-websocket
```  

2. Inicie o aplicativo Spring Boot:
```shell  
./mvnw spring-boot:run
```  

4. O servidor WebSocket estará em execução na URL ws://localhost:8080/websocket


##### Uso do WebSocket

1. Via javascript, usando um cliente WebSocket para se conectar ao servidor e enviar mensagens JSON em formato de string.  O servidor WebSocket converterá essas mensagens em objetos e as retornará como strings JSON.

Aqui um exemplo em JavaScript:

```javascript  
const socket = new WebSocket('ws://localhost:8080/websocket');  
  
socket.onopen = () => {  
// Conexão estabelecida com sucesso.  
const message = {  
message: 'qualquer_texto',  
};  
  
// Envie a mensagem JSON como uma string.  
socket.send(JSON.stringify(message));  
};  
  
socket.onmessage = (event) => {  
// Receba a resposta do servidor.  
const response = JSON.parse(event.data);  
console.log('Resposta do servidor:', response);  
};  
  
socket.onclose = (event) => {  
// A conexão foi fechada.  
console.log('Conexão WebSocket fechada:', event);  
};  
```  

- Talvez você precise configurar o TLS pra funcionar corretamente.


2. Ou [usar o Postman](https://learning.postman.com/docs/sending-requests/websocket/websocket/)
