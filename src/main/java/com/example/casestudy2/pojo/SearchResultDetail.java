package com.example.casestudy2.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "search_result_detail")
public class SearchResultDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String imgUrl;

    @ElementCollection
    @CollectionTable(name = "result_details", joinColumns = @JoinColumn(name = "search_result_detail_id"))
    @Column(name = "result")
    private List<String> results;

    @Column(nullable = false)
    private LocalDate searchDate;

    @ManyToOne
    @JoinColumn(name = "search_result_id", nullable = false)
    private SearchResult searchResult;
    @Column(nullable = false)
    private Integer runNumber;
}
