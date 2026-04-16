# Projeto QA - Automação

Projeto de estudos para automação de testes utilizando Java, Selenium, Cucumber, PostgreSQL, XML e BDD.

## Objetivo

Criar um projeto completo de automação de testes para praticar:

* Automação web com Selenium
* Estrutura BDD com Cucumber
* Integração com banco PostgreSQL
* Leitura e validação de XML
* Organização do projeto com Page Object
* Versionamento no GitHub
* Criação de casos de teste para Jira e Zephyr

---

# Tecnologias Utilizadas

* Java 25
* Maven
* Selenium WebDriver
* Cucumber
* JUnit
* PostgreSQL
* XML
* VS Code
* Git e GitHub
* Jira
* Zephyr

---

# Estrutura do Projeto

```text
src
 ┣ test
 ┃ ┣ java
 ┃ ┃ ┣ pages
 ┃ ┃ ┃ ┗ HomePage.java
 ┃ ┃ ┣ runners
 ┃ ┃ ┃ ┗ RunnerTest.java
 ┃ ┃ ┣ steps
 ┃ ┃ ┃ ┗ LoginSteps.java
 ┃ ┃ ┣ tests
 ┃ ┃ ┃ ┣ DatabaseTest.java
 ┃ ┃ ┃ ┗ XmlTest.java
 ┃ ┃ ┗ utils
 ┃ ┃   ┣ DatabaseUtils.java
 ┃ ┃   ┗ XmlUtils.java
 ┃ ┗ resources
 ┃   ┣ config
 ┃   ┃ ┣ database-example.properties
 ┃   ┃ ┗ database.properties
 ┃   ┣ data
 ┃   ┃ ┗ cliente.xml
 ┃   ┗ features
 ┃     ┗ login.feature
```

---

# Dependências do Projeto

As dependências são gerenciadas pelo Maven no arquivo `pom.xml`.

Principais bibliotecas:

* Selenium WebDriver
* Cucumber Java
* Cucumber JUnit
* PostgreSQL Driver
* WebDriverManager

Para instalar todas as dependências:

```bash
mvn clean install
```

---

# BDD com Cucumber

O projeto utiliza BDD (Behavior Driven Development), permitindo escrever cenários de teste em linguagem simples.

Arquivo:

```text
src/test/resources/features/login.feature
```

Conteúdo:

```gherkin
# language: pt

Funcionalidade: Login simples

  Cenário: Abrir página inicial
    Dado que o usuário acessa a página inicial
    Quando a página carregar completamente
    Então o título da página deve ser exibido corretamente
```

### Significado

* Dado → estado inicial
* Quando → ação executada
* Então → validação esperada

---

# Runner do Cucumber

Arquivo:

```text
src/test/java/runners/RunnerTest.java
```

```java
package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        monochrome = true,
        plugin = {"pretty"}
)
public class RunnerTest {
}
```

---

# Automação com Selenium

Arquivo:

```text
src/test/java/steps/LoginSteps.java
```

```java
package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;

public class LoginSteps {

    WebDriver driver;
    HomePage homePage;

    @Dado("que o usuário acessa a página inicial")
    public void que_o_usuario_acessa_a_pagina_inicial() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://example.com");
        homePage = new HomePage(driver);
    }

    @Quando("a página carregar completamente")
    public void a_pagina_carregar_completamente() {
        driver.manage().window().maximize();
    }

    @Então("o título da página deve ser exibido corretamente")
    public void o_titulo_da_pagina_deve_ser_exibido_corretamente() {
        String titulo = homePage.obterTituloPagina();
        Assert.assertTrue(titulo.length() > 0);
        driver.quit();
    }
}
```

---

# Page Object

Arquivo:

```text
src/test/java/pages/HomePage.java
```

```java
package pages;

import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String obterTituloPagina() {
        return driver.getTitle();
    }
}
```

---

# Integração com PostgreSQL

Banco criado:

```sql
CREATE DATABASE qa_teste;
```

Tabela:

```sql
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(150)
);
```

Inserção de dados:

```sql
INSERT INTO usuarios (nome, email)
VALUES ('Elton Souza', 'elton@email.com');
```

Arquivo de configuração:

```text
src/test/resources/config/database.properties
```

```properties
db.url=jdbc:postgresql://localhost:5432/qa_teste
db.user=postgres
db.password=SUA_SENHA
```

Classe de conexão:

```java
package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseUtils {

    public static Connection conectar() throws Exception {

        Properties properties = new Properties();

        InputStream input = DatabaseUtils.class
                .getClassLoader()
                .getResourceAsStream("config/database.properties");

        properties.load(input);

        String url = properties.getProperty("db.url");
        String usuario = properties.getProperty("db.user");
        String senha = properties.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, senha);
    }
}
```

Teste do banco:

```java
package tests;

import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        try {

            Connection conexao = DatabaseUtils.conectar();
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
            }

            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Resultado esperado:

```text
ID: 1
Nome: Elton Souza
Email: elton@email.com
```

---

# Teste de Relacionamento com XML

Arquivo XML:

```text
src/test/resources/data/cliente.xml
```

```xml
<cliente>
    <nome>Elton Souza</nome>
    <email>elton@email.com</email>
    <cidade>Barueri</cidade>
</cliente>
```

Classe para leitura do XML:

```java
package utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

public class XmlUtils {

    public static String lerTag(String caminhoArquivo, String tag) {

        try {
            File arquivo = new File(caminhoArquivo);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(arquivo);

            return documento.getElementsByTagName(tag)
                    .item(0)
                    .getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
```

Teste do XML:

```java
package tests;

import utils.XmlUtils;

public class XmlTest {

    public static void main(String[] args) {

        String nome = XmlUtils.lerTag(
                "src/test/resources/data/cliente.xml",
                "nome"
        );

        String email = XmlUtils.lerTag(
                "src/test/resources/data/cliente.xml",
                "email"
        );

        System.out.println("Nome XML: " + nome);
        System.out.println("Email XML: " + email);
    }
}
```

Resultado esperado:

```text
Nome XML: Elton Souza
Email XML: elton@email.com
```

---

# Segurança

O arquivo com a senha real do banco não deve subir para o GitHub.

Arquivo ignorado:

```text
src/test/resources/config/database.properties
```

Arquivo de exemplo:

```text
src/test/resources/config/database-example.properties
```

Conteúdo:

```properties
db.url=jdbc:postgresql://localhost:5432/qa_teste
db.user=postgres
db.password=coloque_sua_senha_aqui
```

Arquivo `.gitignore`:

```text
target/
.idea/
.vscode/
*.class

src/test/resources/config/database.properties
```

---

# Como Executar

Executar todos os testes:

```bash
mvn test
```

Executar o teste do banco:

* Abrir `DatabaseTest.java`
* Clicar em `Run`
* Escolher `main() method`

Executar o teste do XML:

* Abrir `XmlTest.java`
* Clicar em `Run`
* Escolher `main() method`

---

# Casos de Teste para Jira e Zephyr

## CT-001 - Validar abertura da página inicial

Passos:

1. Abrir a aplicação
2. Carregar a página inicial
3. Validar título

Resultado esperado:

* Página carregada corretamente

## CT-002 - Validar leitura do banco PostgreSQL

Passos:

1. Conectar ao banco
2. Executar SELECT
3. Validar usuário retornado

Resultado esperado:

* Usuário encontrado na base

## CT-003 - Validar leitura do XML

Passos:

1. Abrir o arquivo XML
2. Ler tag nome
3. Ler tag email

Resultado esperado:

* Nome e email corretos

---

# Resultado Final

Este projeto demonstra conhecimentos em:

* Automação de testes com Selenium e Java
* BDD com Cucumber
* Page Object Model
* PostgreSQL
* XML
* GitHub
* Jira e Zephyr

Projeto criado para prática e apresentação em entrevistas de QA.
