package com.example.casestudy2.dto;

import lombok.Data;

import java.util.List;
@Data
public class SearchResultDTO {
    private Integer runNumber;
    private String searchKeywords;
    private String platform;
    private String matchingPattern;
    private String matchKeywords;
    private String remarks;
    private List<SearchResultDetailDTO> results;
}
