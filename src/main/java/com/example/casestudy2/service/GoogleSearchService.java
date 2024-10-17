package com.example.casestudy2.service;

import com.example.casestudy2.pojo.Search;
import com.example.casestudy2.pojo.SearchResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoogleSearchService {

    @Autowired
    private SearchResultDetailService searchResultDetailService;

    @Autowired
    private SearchResultService searchResultService;

    @Autowired
    private SearchService searchService;

    @Scheduled(cron = "0 * * * * ?")
    public void performSearch() {
        // Tự động cấu hình WebDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        Search search = searchService.findById(1);

        try {
            // Truy cập Google
            driver.get("https://www.search.yahoo.com");

            // Tìm và điền từ khóa vào ô tìm kiếm
            WebElement searchBox = driver.findElement(By.name("p"));
            searchBox.sendKeys(search.getSearchKeywords());

            // Đợi gợi ý xuất hiện
            Thread.sleep(2000);

            // Lấy danh sách gợi ý từ Google
            List<WebElement> suggestions = driver.findElements(By.cssSelector(".compTitle span"));

            // Khởi tạo biến để nối các gợi ý lại thành một chuỗi
            StringBuilder suggestionTextBuilder = new StringBuilder();

            for (WebElement suggestion : suggestions) {
                WebElement titleElement = suggestion.findElement(By.tagName("span"));
                if (titleElement != null) {
                    String suggestionText = titleElement.getText();
                    if (suggestionTextBuilder.length() > 0) {
                        suggestionTextBuilder.append(", ");
                    }
                    suggestionTextBuilder.append(suggestionText);
                }
            }

            // Lấy toàn bộ chuỗi kết quả tìm kiếm sau khi duyệt xong
            String concatenatedSuggestions = suggestionTextBuilder.toString();

            // Chụp màn hình
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Lưu ảnh chụp màn hình vào thư mục resources/screenshots
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            Path screenshotDir = Paths.get(currentPath.toString(), "src", "main", "resources", "screenshots");

            // Tạo thư mục nếu chưa tồn tại
            File directory = new File(screenshotDir.toString());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Đặt tên file ảnh với timestamp
            File destination = new File(screenshotDir.toString(), "screenshot_" + System.currentTimeMillis() + ".png");
            ImageIO.write(ImageIO.read(screenshot), "png", destination);

            // Tạo hoặc lấy SearchResult
            SearchResult searchResult = new SearchResult();
            searchResult.setSearch(search);
            searchResultService.saveSearchResult(searchResult);

            // Lưu đường dẫn ảnh và kết quả tìm kiếm vào cơ sở dữ liệu
            String imgUrl = destination.getAbsolutePath();
            searchResultDetailService.createSearchResultDetail(searchResult, imgUrl, concatenatedSuggestions);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}