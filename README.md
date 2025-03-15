## Descrição
DSCommerce é um sistema de comércio online no qual um usuário cliente pode visualizar produtos, montar um carrinho de compras e registrar o pedido, enquanto o administrador pode fazer a mesma coisa e também gerenciar os produtos, categorias e pedidos cadastrados no sistema.

O sistema deste projeto possui implementação apenas no backend usando API REST para comunicação entre cliente e servidor. O sistema possui comunicação com banco de dados usando Spring Data JPA, e também restrições de segurança usando Spring Security junto com OAuth2, onde o usuário envia seus dados de acesso ao servidor e recebe um token JWT para fazer futuras requisições que exigem usuário logado.

## Diagrama de classe
![diagrama de classe](https://github.com/user-attachments/assets/c8e212fc-7daf-4e74-9ac4-b2e24291866e)

## Diagrama de caso de uso
![diagrama de caso de uso](https://github.com/user-attachments/assets/5a9881ca-920a-4bb9-ae06-6a1920b360d1)

## Tecnologias utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- OAuth2
