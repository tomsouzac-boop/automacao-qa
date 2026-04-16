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