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

    String[] perfis_exs = {"bonfim", "gabriel", "teste"};

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
        WebElement sign_in = driver.findElement(By.cssSelector("[value='Entrar']"));
        sign_in.click();
        WebElement yes = driver.findElement(By.cssSelector("[value='Sim']"));
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
        EQUIPAMENTOS,
        USUARIOS,
        AJUDA,
    }

    String[] abas_paths = {
        "",
        "/html/body/app-root/app-home/div/app-sidebar/div/mat-sidenav-container/mat-sidenav/div/div[2]/button",
        "/html/body/app-root/app-home/div/app-sidebar/div/mat-sidenav-container/mat-sidenav/div/div[3]/button",
        "/html/body/app-root/app-home/div/app-sidebar/div/mat-sidenav-container/mat-sidenav/div/div[4]/button",
        "/html/body/app-root/app-home/div/app-sidebar/div/mat-sidenav-container/mat-sidenav/div/div[5]/button",
        ""
    };

    void mudarAba(ABA button_index) {
        System.out.println(button_index.ordinal());
        System.out.println(abas_paths[button_index.ordinal()]);
        WebElement button = driver.findElement(By.xpath(abas_paths[button_index.ordinal()]));
        System.out.println(button);
        try_and_click(10, button);
    }

    int getRangeList(){
        String number = driver.findElement(By.className("mat-mdc-paginator-range-label")).getText();
        int n = Integer.valueOf(number.substring(9));
        return n;
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
        DesiredCapabilities chrome = new DesiredCapabilities();
//        options.addArguments("--user-data-dir=C:\\Users\\gabriel.moraes\\AppData\\Local\\Google\\Chrome\\User Data");
        WebDriverManager.chromedriver().setup();
//        options.addArguments("--force-device-scale-factor=0.5");
//        options.addArguments("--disable-extensions");
//        chrome.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.get("https://prylab.eldorado.org.br/PryLab-Front-QA/home");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.tagName("button")).click();
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

    String procurarAsteriscos() {
        int count = 0;
        List<WebElement> div = driver.findElements(By.tagName("div"));
        List<WebElement> label = driver.findElements(By.tagName("label"));

        for(int i = 0; i < div.size(); i++) {
            try {
                if (div.get(i).getText().contains("*")) {
                    count += 1;
                }
            } catch (Exception e) {

            }
        }
        for(int i = 0; i < label.size(); i++) {
            try {
                if (div.get(i).getText().contains("*")) {
                    count += 1;
                }
            } catch (Exception e) {

            }
        }
        return "Foram encontrados " + Integer.toString(count) + " asteriscos.";
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

    boolean try_and_click(int n, WebElement button){
        for (int i = 0; i < n; i++) {
            try {
                button.click();
                System.out.println("Deu certo!");
                return true;
            } catch (ElementClickInterceptedException e ) {
                System.out.println("Interceptou o click!");
                return false;
            } catch (StaleElementReferenceException e) {
                System.out.println("Não encontrou o elemento!");
                return false;
            } catch (NoSuchElementException e) {
                System.out.println("Não encontrou o elemento!");
                return false;
            } catch (Exception e) {
                System.out.println("Erro!");
                return false;
            }
        }
        return false;
    }

    void limparEnsaio(int limit){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        List<WebElement> list = driver.findElements(By.tagName("tr"));
        List<WebElement> deletes = driver.findElements(By.className("last-item"));
        List<WebElement> icons;

        for (int i = 0; i < limit; i++) {
            System.out.println("Rodei!");
            if (deletes.size() > 0) {
                icons = deletes.get(0).findElements(By.tagName("mat-icon"));
                try_and_click(10, icons.get(1));
                try {
                    try_and_click(10, driver.findElement(By.className("warn")));
                } catch (Exception e) {
                    continue;
                }
                deletes = driver.findElements(By.className("last-item"));
            }
        }

    }

    void limparEPIS(){
        List<WebElement> list = driver.findElements(By.tagName("tr"));
        System.out.println(list.size());
        WebElement current_row;

        while (list.size() != 1) {
            System.out.println("TAMANHO DA LISTA:" + Integer.toString(list.size()));
            current_row = list.get(1).findElements(By.tagName("mat-icon")).get(1);
            if (try_and_click(10, current_row)) {
                try_and_click(10, driver.findElement(By.className("warn")));
            }
            list = driver.findElements(By.tagName("tr"));
        }
    }

    String add_epi = "/html/body/app-root/app-epi/div/div[3]/div[1]/button";
    String epi_input = "/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div/mat-form-field/div[1]/div/div[2]/input";
    String epi_save = "/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/mat-dialog-actions/button[2]";
    void criarEPI(String nome) {
        try_and_click(5, driver.findElement(By.xpath(add_epi)));
        try {
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[1]/mat-form-field/div[1]/div[2]/div/input")).sendKeys(nome);
        }catch (Exception e) {

        }
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/mat-dialog-container/div/div/div/div[2]/button[2]")).click();
    }

    void criarEPIS(int n){

        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            driver.findElement(By.xpath(add_epi)).click();
            String nome = epi_exs[rnd.nextInt(epi_exs.length)];
            System.out.println(nome);
            try {
                driver.findElement(By.xpath(epi_input)).sendKeys(nome);
            }catch (Exception e) {
                continue;
            }
            driver.findElement(By.xpath(epi_save)).click();
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

    void mudarAbaUsuario(int aba_id) {
        // Usuário -> 0
        // Perfil -> 1
        switch (aba_id) {
            case 0:
                try_and_click(10, driver.findElement(By.xpath("/html/body/app-root/app-user-profile/div/mat-tab-group/mat-tab-header/div/div/div/div[1]")));
                break;
            case 1:
                try_and_click(10, driver.findElement(By.xpath("/html/body/app-root/app-user-profile/div/mat-tab-group/mat-tab-header/div/div/div/div[2]")));
                break;
        }


    }

    private void criarNovoPerfil() {
        try {
            try_and_click(10, driver.findElement(By.className("new-delete-profile")));
        } catch (Exception e) {

        }
        System.out.println(driver.findElements(By.tagName("input")).size());
        String perfil_nome = pick_random(perfis_exs);
        driver.findElements(By.tagName("input")).get(3).sendKeys("perfil-" + perfil_nome);
    }

    @Test
    void USR_01(){
        mudarAba(ABA.USUARIOS);
        procurarAsteriscos();
        WebElement titulo = driver.findElement(By.className("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Gerenciamento de usuários", aba);
    }

    @Test
    void CHM_03(){
        mudarAba(ABA.CHAMADOS);
        WebElement titulo = driver.findElement(By.className("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Contrato de Serviço", aba);
    }


    @Test
    void ENS_01(){
        mudarAba(ABA.ENSAIOS);
        WebElement titulo = driver.findElement(By.className("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Ensaio", aba);
    }

    @Test
    void ENS_03(){
        int n_target = 20;
        mudarAba(ABA.ENSAIOS);
        limparEnsaio(50);
        criarEnsaios(n_target);
        limparEnsaio(10);
    }

    @Test
    void EPI_01(){
        mudarAba(ABA.MANUTENCAO);
        System.out.println(procurarAsteriscos());
        WebElement titulo = driver.findElement(By.className("form-title"));
        String aba = titulo.findElement(By.tagName("label")).getText();
        assertEquals("Equipamento de Proteção Individual", aba);
    }

    @Test
    void EPI_03(){
        mudarAba(ABA.MANUTENCAO);
        int n_target = 10;
        limparEPIS();
        criarEPIS(n_target);
        limparEPIS();
        assertEquals(0, driver.findElements(By.tagName("tr")).size());

    }


    @Test
    void USR_PERFIL01(){
        mudarAba(ABA.USUARIOS);
        procurarAsteriscos();
        mudarAbaUsuario(1);
        criarNovoPerfil();
    }


}