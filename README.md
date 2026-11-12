\# Sistema de Gestão de Restaurante - POO



Trabalho prático da disciplina \*\*GCET1065 - Princípios de Orientação a Objetos\*\*. 

O projeto consiste no desenvolvimento iterativo e incremental de um sistema para informatizar o processo de negócio de um restaurante, aplicando conceitos de abstração, encapsulamento, herança, polimorfismo, classes abstratas, interfaces e associações.



\## 👥 Integrantes do Grupo

\* \*\*Nome da Pessoa 1\*\* - \*Foco: Modelagem UML, Núcleo de Pedidos e Cardápio.\*

\* \*\*Nome da Pessoa 2\*\* - \*Foco: Modelagem UML, Hierarquia de Funcionários e Formas de Pagamento.\*



\---



\## ⚙️ Requisitos e Como Executar



\*\*Pré-requisitos:\*\*

\* Java JDK 11 ou superior instalado.

\* Terminal/Console ou uma IDE (VS Code, IntelliJ, Eclipse).



\*\*Passo a passo para execução:\*\*

1\. Clone o repositório:

&#x20;  ```bash

&#x20;  git clone https://github.com/seu-usuario/seu-repositorio.git

Navegue até a pasta do projeto e compile os arquivos Java:



bash

javac -d bin src/modelo/\*.java src/principal/\*.java

Execute o sistema:



bash

java -cp bin principal.Main

(Nota: Se estiver usando uma IDE, basta abrir a pasta do projeto e executar a classe Main.java)



📌 Premissas e Decisões de Projeto (Entrega 1)

O documento de regras de negócio apresenta lacunas intencionais. Para a modelagem e codificação desta primeira etapa, o grupo adotou as seguintes premissas:



Devolução de Itens: A regra diz que pratos ou bebidas podem ser devolvidos individualmente. Para simplificar o escopo do Módulo de Pedidos nesta Entrega 1, o atributo devolvido foi colocado na classe Pedido (booleano). Nota: Para a Entrega 2, avaliaremos a criação da classe ItemPedido para permitir devoluções parciais (ex: devolver só o suco).



Atendentes (Garçom e Telefonista): O sistema exige que todo pedido identifique quem o colheu. A classe Pedido recebe um objeto do tipo Funcionario (polimorfismo). Criamos a classe Telefonista herdando de Funcionario para atender à regra de pedidos por telefone.



Agregação de Itens no Pedido: Um Pedido agrega uma lista de ItemCardapio. Como ItemCardapio é uma classe abstrata, ela só pode ser instanciada através de suas filhas (Prato e Bebida), garantindo a especialização.



Nota e Pagamento: A classe Nota depende da interface FormaPagamento. Isso permite que o pagamento seja processado via Cartão, Cheque ou Dinheiro sem que a classe Nota precise conhecer os detalhes de implementação de cada um, respeitando o Princípio da Inversão de Dependência.



📐 Arquitetura e Diagrama de Classes

O projeto foi dividido nos seguintes pacotes:



modelo: Contém todas as entidades do sistema (Cliente, Pedido, Funcionario, etc.).



principal: Contém a classe Main com a interface de console para testes.



Diagrama de Classes (UML)

O diagrama foi elaborado utilizando a ferramenta Mermaid JS, garantindo versionamento junto ao código.
