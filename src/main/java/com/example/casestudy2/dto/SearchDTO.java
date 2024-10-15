package com.example.casestudy2.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchDTO {
    private Integer id;
    @NotNull
    private String searchKeywords;
    @NotNull
    private String matchKeywords;
    @NotNull
    private Integer performanceRewardDays;
    @Pattern(regexp = "Google|Yahoo", message = "Platform must be either 'Google' or 'Yahoo'")
    private String platform;
    @Pattern(regexp = "Partial match|Unanimous", message = "Matching pattern must be either 'Partial match' or 'Unanimous'")
    private String matchingPattern;
    @Pattern(regexp = "PC|Smartphone", message = "Device must be either 'PC' or 'Smartphone'")
    private String device;

    private String remarks;
}
