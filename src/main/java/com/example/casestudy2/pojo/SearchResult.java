package com.example.casestudy2.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@Table(name = "search_results")
public class SearchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String imgUrl;
    @ElementCollection
    @CollectionTable(name = "search_result_items", joinColumns = @JoinColumn(name = "search_result_id"))
    @Column(name = "result")
    private List<String> results;
    @Column(nullable = false)
    private LocalDate searchDate;

    @ManyToOne
    @JoinColumn(name = "search_id", nullable = false)
    private Search search;
}
