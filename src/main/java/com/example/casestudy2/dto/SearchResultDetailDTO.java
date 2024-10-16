package com.example.casestudy2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class SearchResultDetailDTO {
    private String searchDate;
    private String imgUrl;
    private List<String> results;
}
