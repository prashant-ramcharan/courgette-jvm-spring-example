package support;

import io.github.pramcharan.wd.binary.downloader.WebDriverBinaryDownloader;
import io.github.pramcharan.wd.binary.downloader.enums.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class TestSupport {
    private WebDriver driver;

    public TestSupport() {
        WebDriverBinaryDownloader.create().downloadLatestBinaryAndConfigure(BrowserType.CHROME);

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver = new ChromeDriver(options);
    }

    public WebDriver getDriver() {
        return driver;
    }
}