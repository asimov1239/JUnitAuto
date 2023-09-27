package org.pryTesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PryTest {
    ChromeDriver driver;
    String[] emails = {"",
    ""};

    String user_tab = "";

    String[] epi_exs = {"Óculos", "Luvas", "Roupa", "Teste", "Capacete", "Máscara","óculos", "luvas", "roupa", "capacete"};

    String[] ensaios_exs = {"Alpha", "Beta", "Charlie", "delta", "Epsilon", "Foxtrot", "Gamma", "Hyperion", "ipsilon", "June", "Kappa", "Lambda", "Mu", "Nu", "Omicron", "Pi", "Queue", "Rho", "Sigma", "Tau",
    "Upsilon", "Phi", "chi", "psi" };

    String cs_tab = "/html/body/app-root/app-contract-service/div/app-sidenav/div/mat-sidenav-container/mat-sidenav/div/button[2]/span[1]";

    String pick_random(String[] args){
        Random random = new Random();
        String randomElement = args[random.nextInt(args.length)];
        return randomElement;
    }

    void log_in_microsoft() {
        driver.findElement(By.id("i0116")).sendKeys("gabriel@mbsoftgmail.onmicrosoft.com");
        driver.findElement(By.id("idSIButton9")).click();
        driver.findElement(By.id("i0118")).sendKeys("ipe@1999");
        WebElement sign_in = driver.findElement(By.cssSelector("[value='Sign in']"));
        sign_in.click();
        WebElement yes = driver.findElement(By.cssSelector("[value='Yes']"));
        yes.click();
    }

    void add_user_admin() {
        driver.get("https://serv192.corp.eldorado.org.br/PryLab-Front-QA/admin");

    }

    WebElement getRandomOption(List<WebElement> list){
        Random rand = new Random();
        WebElement randomElement = list.get(rand.nextInt(list.size()));
        return randomElement;
    }

    void matSelectInput(String id){
        WebElement select = driver.findElement(By.id(id));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        select.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div"))));
        List<WebElement> options = driver.findElements(By.tagName("mat-option"));
        WebElement option = getRandomOption(options);
        for (int i = 0; i < 6; i++) {
            try {
                option.click();
                break;
            } catch (Exception e) {

            }
        }
    }

    void matSelectInputMult(String id){
        WebElement select = driver.findElement(By.id(id));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        select.click();
        List<WebElement> options = driver.findElements(By.tagName("mat-option"));
        WebElement option;
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(8); i++){
            option = getRandomOption(options);
            try {
                option.click();
            } catch (Exception e) {
                continue;
            }
        }
        driver.findElement(By.className("cdk-overlay-container")).click();

    }

    void zoom_out() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom = '75%'");
    }

    enum ABA {
        DASHBOARD,
        CHAMADOS,
        ENSAIOS,
        MANUTENCAO,
        USUARIOS,
        AJUDA,
    }
    void mudarAba(ABA button_index) {
        WebElement side_nav = driver.findElement(By.tagName("mat-sidenav"));
        List<WebElement> buttons = side_nav.findElements(By.tagName("button"));
        for (int j = 0; j < buttons.size(); j++) {
            if (j == button_index.ordinal()) {
                System.out.println("Teste");
                for (int i = 0; i < 20; i++) {
                    try {
                        buttons.get(j).click();
                    } catch (StaleElementReferenceException e) {
                    }
                }
            }
        }
    }

    int getRangeList(){
        String number = driver.findElement(By.className("mat-mdc-paginator-range-label")).getText();
        int n = Integer.valueOf(number.substring(9));
        return n;
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        DesiredCapabilities chrome = new DesiredCapabilities();
//        options.addArguments("--user-data-dir=C:\\Users\\gabriel.moraes\\AppData\\Local\\Google\\Chrome\\User Data");
        WebDriverManager.chromedriver().setup();
//        options.addArguments("--force-device-scale-factor=0.5");
//        options.addArguments("--disable-extensions");
//        chrome.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.get("https://prylab.eldorado.org.br/PryLab-Front-QA/home");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        log_in_microsoft();
//        zoom_out();
    }

    void exit() {
        driver.quit();
    }

    void validateTitle() {
        String title = driver.getTitle();
        assertEquals("PryLab", title);
    }


    void validateFieldSize(){
        driver.findElement(By.id("mat-input-0")).sendKeys("TestTestTestTestTestTestTestTest");
        assertEquals(20, driver.findElement(By.id("mat-input-0")).getText().length());
    }


    void clearChamados(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement add = driver.findElement(By.xpath("/html/body/app-root/app-home/div/app-sidenav/div/mat-sidenav-container/mat-sidenav/div/button[2]"));
        wait.until(ExpectedConditions.visibilityOf(add));

        List<WebElement> cells = driver.findElements(By.tagName("tr"));
        WebElement icons;
        WebElement target;
        cells.remove(0);


        while (cells.size() != 0){
            target = cells.get(0);
            icons = target.findElement(By.className("last-item"));
            icons.findElements(By.tagName("mat-icon")).get(3).click();
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/app-delete-modal/div/div/div/button[2]")).click();
            cells.remove(0);
            cells = driver.findElements(By.tagName("tr"));
            cells.remove(0);
        }

    }

    void criarChamado() {
        for (int i = 0; i < 21; i++){
            driver.findElement(By.className("add-cs-button")).click();
            driver.findElement(By.id("name")).sendKeys("Teste" + Integer.toString(i));
            matSelectInput("productTypeGuid");
            matSelectInput("priority");
            matSelectInput("statusGuid");
            driver.findElement(By.xpath("/html/body/app-root/app-contract-service-form/div/form/div/button")).click();
        }
    }



    void limparEnsaio(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        List<WebElement> list = driver.findElements(By.tagName("tr"));

        for (int i = 0; i < 100; i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/app-root/app-essay/div/section/div[2]/table/tbody/tr[3]"))));
        }

        while (list.size() != 0) {
            for (int j = 0; j < 5; j++) {
                try {
                    driver.findElement(By.xpath("/html/body/app-root/app-essay/div/section/div[2]/table/tbody/tr[1]/td[4]/div/mat-icon[2]")).click();
                    break;
                } catch (Exception e) {

                }
            }
            try {
                driver.findElement(By.className("warn")).click();
            }catch (Exception e) {
            }
            list = driver.findElements(By.tagName("tr"));
            System.out.println(list.size());
        }

    }

    void limparEPIS(){
        List<WebElement> list = driver.findElements(By.tagName("mat-row"));

        while (list.size() != 0) {
            for (int j = 0; j < 5; j++) {
                try {
                    list.get(0).findElements(By.tagName("mat-icon")).get(1).click();
                    break;
                } catch (Exception e) {

                }
            }
            try {
                driver.findElement(By.className("warn")).click();
            }catch (Exception e) {
            }
            list = driver.findElements(By.tagName("mat-row"));
        }
    }

    void criarEPI(String nome) {
        driver.findElement(By.className("add-epi-button")).click();
        try {
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[1]/mat-form-field/div[1]/div[2]/div/input")).sendKeys(nome);
        }catch (Exception e) {

        }
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[2]/button[2]")).click();
    }

    void criarEPIS(int n){

        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            driver.findElement(By.className("add-epi-button")).click();
            String nome = epi_exs[rnd.nextInt(epi_exs.length)];
            System.out.println(nome);
            try {
                driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[1]/mat-form-field/div[1]/div[2]/div/input")).sendKeys(nome);
            }catch (Exception e) {
                continue;
            }
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[2]/button[2]")).click();
        }

    }

    void criarEnsaios(int n){
        Random rnd = new Random();
        for (int i = 0; i < n; i++ ){
            driver.findElement(By.className("add-essay-button")).click();
            String nome = ensaios_exs[rnd.nextInt(ensaios_exs.length)];
            driver.findElement(By.id("name")).sendKeys(nome);
            matSelectInputMult("epis");
            matSelectInput("status");
            driver.findElement(By.xpath("/html/body/app-root/app-essay-form/div/form/div/button")).click();
        }

    }

    @Test
    void USR_01(){
        mudarAba(ABA.USUARIOS);
        WebElement titulo = driver.findElement(By.className("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Gerenciamento de usuários", aba);
    }

    @Test
    void CHM_03(){
        mudarAba(ABA.CHAMADOS);
        WebElement titulo = driver.findElement(By.id("title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Contrato de Serviço", aba);
    }


    @Test
    void ENS_01(){
        mudarAba(ABA.ENSAIOS);
        WebElement titulo = driver.findElement(By.id("title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Ensaio", aba);
    }

    @Test
    void ENS_03(){
        int n_target = 10;
        mudarAba(ABA.ENSAIOS);
//        limparEnsaio();
        criarEnsaios(n_target);
//        int n_ensaios = getRangeList();
//        limparEnsaio();

//        assertEquals(n_ensaios, getRangeList());
    }

    @Test
    void EPI_01(){
        mudarAba(ABA.MANUTENCAO);
        WebElement titulo = driver.findElement(By.id("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Equipamento de Proteção Individual", aba);
    }

    @Test
    void EPI_03(){
        mudarAba(ABA.MANUTENCAO);
        int n_target = 20;
//        limparEPIS();
        criarEPIS(n_target);
        assertEquals(0, driver.findElements(By.tagName("mat-row")).size());

    }


}