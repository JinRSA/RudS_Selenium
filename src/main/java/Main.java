import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try (InputStream input = new FileInputStream("src/main/resources/resources.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String path2ChromeDriver = properties.getProperty("path2ChromeDriver");
            System.out.println("Путь к драйверу: \"" + path2ChromeDriver + "\"\n");
            System.setProperty("webdriver.chrome.driver", path2ChromeDriver);
        } catch (Exception ignored) {}
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // 1.
        driver.get("https://avito.ru");
        driver.manage().window().maximize();
        // 2.
        By by = By.id("category");
        Select categoryDropDownList = new Select(driver.findElement(by));
        String visibleText = "Оргтехника и расходники";
        categoryDropDownList.selectByVisibleText(visibleText);
        // 3.
        by = By.xpath("//input[@data-marker=\"search-form/suggest\"]");
        WebElement search = driver.findElement(by);
        String searchStr = "Принтер";
        search.sendKeys(searchStr);
        // 4.
        by = By.xpath("//div[@data-marker=\"search-form/region\"]");
        driver.findElement(by).click();
        // 5.
        by = By.xpath("//input[@data-marker=\"popup-location/region/input\"]");
        WebElement searchCity = driver.findElement(by);
        String searchCityStr = "Владивосток";
        searchCity.sendKeys(searchCityStr);
        by = By.xpath("//strong[text()=\"Владивосток\"]");
        WebElement city = driver.findElement(by);
        city.click();
        by = By.xpath("//button[@data-marker=\"popup-location/save-button\"]");
        driver.findElement(by).click();
        // 6.
        by = By.xpath("//input[@data-marker=\"delivery-filter/input\"]");
        WebElement checkBox = driver.findElement(by);
        if (!checkBox.isSelected())
            checkBox.sendKeys(Keys.SPACE);
        by = By.xpath("//button[@data-marker=\"search-filters/submit-button\"]");
        driver.findElement(by).click();
        // 7.
        by = By.xpath("//select[option[./text()=\"По умолчанию\"]]");
        Select filterByDropDownList = new Select(driver.findElement(by));
        visibleText = "Дороже";
        filterByDropDownList.selectByVisibleText(visibleText);
        // 8.
        by = By.xpath("//h3[@itemprop=\"name\"]");
        List<WebElement> resultsName = driver.findElements(by);
        by = By.xpath("//span[@data-marker=\"item-price\"]");
        List<WebElement> resultsPrice = driver.findElements(by);
        for (int i = 0; i < 3; ++i)
            System.out.println("Наименование: " + resultsName.get(i).getText() + "\nЦена: " + resultsPrice.get(i).getText());

        driver.quit();
    }
}