package com.example.casestudy2.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "searchs")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String searchKeywords;

    @Column(nullable = false)
    private String matchKeywords;

    @Column(nullable = false)
    private Integer performanceRewardDays;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String matchingPattern;

    @Column(nullable = false)
    private String device;

    private String remarks;
    @OneToMany(mappedBy = "search", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SearchResult> searchResults;
}
