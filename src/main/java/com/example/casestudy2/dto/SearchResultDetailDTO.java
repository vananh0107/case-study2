package com.example.casestudy2.dto;

import lombok.Data;

import java.util.List;
@Data
public class SearchResultDetailDTO {
    private String searchDate;
    private String imgUrl;
    private Integer indexFound;
    private List<String> results;
}
