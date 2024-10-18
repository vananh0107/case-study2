package com.example.casestudy2.repo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class SearchCount {
    private Integer runNumber;
    private String searchKeywords;
    private String platform;
    private String matchingPattern;
    private String matchKeywords;
    private String remarks;
    private LocalDate searchDate;
    private String imgUrl;
    private String result;
}