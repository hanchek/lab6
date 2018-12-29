import java.io.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import java.util.regex.Pattern;

public class RozetkaTest {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    public static WebDriver driver;

    @BeforeClass
    public static void startService() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ira\\IdeaProjects\\lab6\\chromedriver.exe");
    }

    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void SearchTest() throws Exception{
        /*
        driver.get("https://rozetka.com.ua");
        WebElement search = driver.findElement(By.xpath("html/body/header/div/div/div[2]/div[3]/form/div[1]/div[2]/input"));

        search.sendKeys("графический планшет");
        search.submit();*/
        driver.get("https://rozetka.com.ua/grafic-tablets/c83199/");
        WebElement minPrice = driver.findElement(By.xpath("//*[@id='price[min]']"));
        WebElement OK = driver.findElement(By.xpath("//*[@id='submitprice']"));
        String minimal = "4000";
        try {
            minPrice.click();
            minPrice.sendKeys(minimal);
            OK.click();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        List<WebElement> prices = driver.findElements(By.xpath("//*[@class = 'g-price-uah']"));

        int count = 0;
        for(WebElement price : prices) {
            if (Integer.valueOf(price.getText().replaceAll("[^0-9]", "")) < Integer.parseInt(minimal)) count++;
        }
        if(count != 0) {
            System.out.println("Filter is not working!");
            System.out.println("The number of prices that didn't fit the filter: " + count);
        }
        try {
            String targetUrl = driver.getCurrentUrl();
            String targetImg= "RozetkaTest.png";
            String command = "C:\\Users\\Ira\\IdeaProjects\\lab6\\phantomjs-2.1.1-windows\\bin\\phantomjs C:\\Users\\Ira\\IdeaProjects\\lab6\\phantomjs-2.1.1-windows\\examples\\rasterize.js "+targetUrl + " " +targetImg;
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
