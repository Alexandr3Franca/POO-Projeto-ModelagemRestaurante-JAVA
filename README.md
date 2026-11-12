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


classDiagram

&#x20;   %% Interface e Pagamento

&#x20;   class FormaPagamento {

&#x20;       <<interface>>

&#x20;       +processarPagamento(valor: double): boolean

&#x20;   }

&#x20;   

&#x20;   class Cartao {

&#x20;       +processarPagamento(valor: double): boolean

&#x20;   }

&#x20;   class Cheque {

&#x20;       +processarPagamento(valor: double): boolean

&#x20;   }

&#x20;   class Dinheiro {

&#x20;       +processarPagamento(valor: double): boolean

&#x20;   }

&#x20;   FormaPagamento <|.. Cartao

&#x20;   FormaPagamento <|.. Cheque

&#x20;   FormaPagamento <|.. Dinheiro



&#x20;   %% Itens do Cardápio (Superclasse e Subclasses)

&#x20;   class ItemCardapio {

&#x20;       <<abstract>> 

&#x20;       -nome: String

&#x20;       -preco: double

&#x20;       -diaDisponivel: String

&#x20;       +ItemCardapio(nome: String, preco: double, diaDisponivel: String)

&#x20;       +getNome(): String

&#x20;       +getPreco(): double

&#x20;   }

&#x20;   class Prato {

&#x20;       -pontoCarne: String

&#x20;   }

&#x20;   class Bebida {

&#x20;       -comGelo: boolean

&#x20;   }

&#x20;   ItemCardapio <|-- Prato

&#x20;   ItemCardapio <|-- Bebida



&#x20;   %% Hierarquia de Funcionários (Classe Abstrata)

&#x20;   class Funcionario {

&#x20;       <<abstract>>

&#x20;       -id: int

&#x20;       -nome: String

&#x20;       +Funcionario(id: int, nome: String)

&#x20;       +getNome(): String

&#x20;   }

&#x20;   

&#x20;   class Garcom {

&#x20;       -exclusivoBebida: boolean

&#x20;       +Garcom(id: int, nome: String, exclusivoBebida: boolean)

&#x20;       +isExclusivoBebida(): boolean

&#x20;   }

&#x20;   

&#x20;   class Telefonista {

&#x20;       +Telefonista(id: int, nome: String)

&#x20;   }

&#x20;   

&#x20;   class Entregador {

&#x20;       -rotaAtual: String

&#x20;       +realizarEntrega(pedidos: List\~Pedido\~): void

&#x20;   }

&#x20;   

&#x20;   class Gerente {

&#x20;       +emitirOrdem(funcionario: Funcionario, ordem: String): void

&#x20;   }

&#x20;   

&#x20;   class Manobrista {

&#x20;       +buscarAutomovel(cliente: Cliente): void

&#x20;   }

&#x20;   

&#x20;   Funcionario <|-- Garcom

&#x20;   Funcionario <|-- Telefonista

&#x20;   Funcionario <|-- Entregador

&#x20;   Funcionario <|-- Gerente

&#x20;   Funcionario <|-- Manobrista



&#x20;   %% Classes Principais codificadas no Módulo de Pedidos

&#x20;   class Cliente {

&#x20;       -id: int

&#x20;       -nome: String

&#x20;       +Cliente(id: int, nome: String)

&#x20;       +getNome(): String

&#x20;   }



&#x20;   class Pedido {

&#x20;       -id: int

&#x20;       -cliente: Cliente

&#x20;       -atendente: Funcionario

&#x20;       -itens: List\~ItemCardapio\~

&#x20;       -devolvido: boolean

&#x20;       +Pedido(id: int, cliente: Cliente, atendente: Funcionario)

&#x20;       +adicionarItem(item: ItemCardapio): void

&#x20;       +registrarDevolucao(): void

&#x20;       +exibirResumo(): void

&#x20;   }



&#x20;   class Nota {

&#x20;       -valorTotal: double

&#x20;       -tempoAtendimento: int

&#x20;       -tempoPermanencia: int

&#x20;       +calcularTotal(): double

&#x20;       +emitirNota(pagamento: FormaPagamento): void

&#x20;   }



&#x20;   %% Relacionamentos

&#x20;   Cliente "1" -- "1..\*" Pedido : faz >

&#x20;   Pedido "\*" -- "1" Funcionario : atende >

&#x20;   Pedido "1" o-- "\*" ItemCardapio : agrega >

&#x20;   Nota "1" -- "1..\*" Pedido : consolida >

&#x20;   Nota ..> FormaPagamento : depende >



