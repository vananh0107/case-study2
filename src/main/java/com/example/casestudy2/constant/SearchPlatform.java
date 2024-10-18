package com.example.casestudy2.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.By;

@Data
@AllArgsConstructor
public class SearchPlatform {
    public static final String GOOGLE_PLATFORM = "Google";
    public static final String YAHOO_PLATFORM = "Yahoo";

    private static final String GOOGLE_URL = "https://www.google.com";
    private static final By GOOGLE_SEARCH_BOX = By.name("q");
    private static final By GOOGLE_SUGGESTION_LOCATOR = By.cssSelector("#Alh6id ul.G43f7e li");

    private static final String YAHOO_URL = "https://search.yahoo.com";
    private static final By YAHOO_SEARCH_BOX = By.name("p");
    private static final By YAHOO_SUGGESTION_LOCATOR = By.cssSelector("ul.sa-list li");

    private String searchUrl;
    private By searchBoxLocator;
    private By suggestionLocator;

    public static SearchPlatform createPlatform(String platform) {
        switch (platform) {
            case GOOGLE_PLATFORM:
                return new SearchPlatform(GOOGLE_URL, GOOGLE_SEARCH_BOX, GOOGLE_SUGGESTION_LOCATOR);
            case YAHOO_PLATFORM:
                return new SearchPlatform(YAHOO_URL, YAHOO_SEARCH_BOX, YAHOO_SUGGESTION_LOCATOR);
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }
}
