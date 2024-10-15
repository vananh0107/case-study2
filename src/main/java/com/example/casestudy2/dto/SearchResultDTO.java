package com.example.casestudy2.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class SearchResultDTO {
    private String searchKeywords;
    private String platform;
    private String matchingPattern;
    private String remarks;

    private LocalDate searchDate;
    private String imgUrl;
    private List<String> results;
}
