package com.example.casestudy2.service;

import com.example.casestudy2.constant.SearchPlatform;
import com.example.casestudy2.pojo.Search;
import com.example.casestudy2.pojo.SearchResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@Service
public class ToolSearchService {

    @Autowired
    private SearchResultDetailService searchResultDetailService;

    @Autowired
    private SearchResultService searchResultService;

    @Autowired
    private SearchService searchService;

    @Scheduled(cron = "0 25 14 * * ?")
    public void performSearch() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            List<Search> searches = searchService.findAll();
            for (Search search : searches) {
                processSingleSearch(driver, search);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public void processSingleSearch(WebDriver driver, Search search) throws IOException {
        //init tool
        SearchPlatform platformConfig = SearchPlatform.createPlatform(search.getPlatform());
        driver.get(platformConfig.getSearchUrl());
        //find element search
        WebElement searchBox = driver.findElement(platformConfig.getSearchBoxLocator());
        searchBox.sendKeys(search.getSearchKeywords());
        //wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(platformConfig.getSuggestionLocator(), 0));
        //get suggest
        String concatenatedSuggestions = getSuggestions(driver, platformConfig.getSuggestionLocator());
        //take picture
        File screenshot = takeScreenshot(driver);
        String imgUrl = saveScreenshot(screenshot);

        SearchResult searchResult = new SearchResult();
        searchResult.setSearch(search);
        searchResultService.create(searchResult);

        searchResultDetailService.create(searchResult, imgUrl, concatenatedSuggestions);
    }

    public String getSuggestions(WebDriver driver, By suggestionLocator) {
        List<WebElement> suggestions = driver.findElements(suggestionLocator);
        StringBuilder suggestionTextBuilder = new StringBuilder();
        for (WebElement suggestion : suggestions) {
            if (suggestion != null && !suggestion.getText().isEmpty()) {
                if (suggestionTextBuilder.length() > 0) {
                    suggestionTextBuilder.append(", ");
                }
                suggestionTextBuilder.append(suggestion.getText());
            }
        }
        return suggestionTextBuilder.toString();
    }

    public File takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public String saveScreenshot(File screenshot) throws IOException {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path screenshotDir = Paths.get(currentPath.toString(), "src", "main", "resources", "screenshots");

        File directory = new File(screenshotDir.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destination = new File(screenshotDir.toString(), "screenshot_" + System.currentTimeMillis() + ".png");
        ImageIO.write(ImageIO.read(screenshot), "png", destination);
        return destination.getAbsolutePath();
    }
}
