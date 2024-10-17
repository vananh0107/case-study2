package com.example.casestudy2.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "search_result_detail")
@Data
public class SearchResultDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String result;

    @Column(nullable = false)
    private LocalDate searchDate;

    @ManyToOne
    @JoinColumn(name = "search_result_id", nullable = false)
    private SearchResult searchResult;
    @Column(nullable = false)
    private Integer runNumber;
}
