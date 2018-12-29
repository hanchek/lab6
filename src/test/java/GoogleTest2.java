import java.io.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import java.util.regex.Pattern;

public class GoogleTest2 {
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
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Test
    public void SearchTest() throws Exception {
        driver.get("https://www.google.com.ua");
        WebElement searchField = driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div/div[1]/div/div[1]/input"));
        String searchWord = "Moyu";
        int pageNumber=1;
        boolean found = true;
        String partial = "facebook";
        try {
            searchField.clear();
            searchField.sendKeys(searchWord);
            searchField.sendKeys(Keys.ENTER);
        }catch (Throwable e)
        {
            e.printStackTrace();
        }

        while (true)
        {
            try{
                driver.findElement(By.partialLinkText(partial));
                break;
            }
            catch(NoSuchElementException e){
                try {
                    driver.findElement(By.xpath("//a[@id='pnnext']")).click();
                    pageNumber++;
                }
                catch (NoSuchElementException e1){
                    System.out.println("No more pages left... No such element on all pages");
                    found = false;
                    break;
                }
            }
        }

        if (found){
            try {
                String targetUrl = driver.getCurrentUrl();
                String targetImg= "GoogleTest2_page" + pageNumber + ".png";
                String command = "C:\\Users\\Ira\\IdeaProjects\\lab6\\phantomjs-2.1.1-windows\\bin\\phantomjs C:\\Users\\Ira\\IdeaProjects\\lab6\\phantomjs-2.1.1-windows\\examples\\rasterize.js "+targetUrl + " " +targetImg;
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Element is on the page number - " + pageNumber);
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