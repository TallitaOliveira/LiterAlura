Descrição do Projeto
Literalura é uma aplicação Java que consome a API do Gutendex para buscar, armazenar e gerenciar informações sobre livros e autores. O projeto foi desenvolvido como parte do desafio da Alura para praticar conceitos de:

Consumo de APIs REST
Persistência de dados com Spring Data JPA
Programação orientada a objetos
Tratamento de exceções

Funcionalidades Principais
✅ Buscar livros por título
✅ Listar todos os livros registrados
✅ Filtrar autores vivos em determinado ano
✅ Listar livros por idioma
✅ Armazenamento em banco de dados PostgreSQL

Tecnologias Utilizadas:
Linguagem: Java 21
Framework: Spring Boot 3.2.4
Banco de dados: PostgreSQL

Bibliotecas principais:
Spring Data JPA
Hibernate
Jackson (para manipulação de JSON)

Ferramentas:
Maven (gerenciamento de dependências)
IntelliJ IDEA (IDE)

Pré-requisitos:
Antes de executar o projeto, você precisará ter instalado:
JDK 21
Maven 3.8+
PostgreSQL 15+

Configurar o banco de dados:
Criar um database chamado literalura
Configurar as credenciais no application.properties

Como Executar:
Clone o repositório:
bash
git clone https://github.com/seu-usuario/literalura.git

Estrutura do Projeto
text
literalura/
├── src/
│   ├── main/
│   │   ├── java/com/alura/literalura/
│   │   │   ├── modelo/       # Entidades JPA
│   │   │   ├── repository/   # Interfaces de repositório
│   │   │   ├── service/      # Lógica de negócio
│   │   │   ├── DTO/         # Objetos de transferência de dados
│   │   │   └── Principal.java # Classe principal
│   │   └── resources/       # Arquivos de configuração
│   └── test/                # Testes
├── pom.xml                  # Configuração Maven
└── README.md                # Este arquivo
Exemplo de Uso
Ao iniciar a aplicação, você verá o menu:

text
===== MENU =====
1 - Buscar livros por título
2 - Listar livros buscados
3 - Listar autores vivos no ano escolhido
4 - Listar livros por idioma
0 - Sair
Digite 1 e o nome de um livro (ex: "Dom Casmurro") para buscar.

Use as outras opções para explorar os dados salvos.

Contribuições são bem-vindas! Siga estes passos:
Faça um fork do projeto
Crie uma branch para sua feature (git checkout -b feature/incrivel)
Commit suas mudanças (git commit -m 'Adiciona nova funcionalidade')
Push para a branch (git push origin feature/incrivel)
Abra um Pull Request

Obrigada! 




