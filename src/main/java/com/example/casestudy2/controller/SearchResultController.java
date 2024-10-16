package com.example.casestudy2.controller;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.dto.SearchResultDetailDTO;
import com.example.casestudy2.service.SearchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchResultController {
    @Autowired
    private SearchResultService searchResultService;

    @GetMapping("/list")
    public String showSearchResults(@RequestParam(value = "monthYear", defaultValue = "2024-10") String monthYear, Model model) {
        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        List<SearchResultDTO> allResults = searchResultService.getResultsByYearAndMonth(10, 2024);
//        Map<Integer, List<SearchResultDTO>> resultsMap = new HashMap<>();
//
//        for (SearchResultDTO result : allResults) {
//            int runNumber = result.getRunNumber();
//            resultsMap.putIfAbsent(runNumber, new ArrayList<>());
//            resultsMap.get(runNumber).add(result);
//        }
//
//        List<List<SearchResultDTO>> results = new ArrayList<>(resultsMap.values());
//
//        model.addAttribute("resultsByDay", results);
//        model.addAttribute("selectedMonthYear", monthYear);
//        model.addAttribute("daysInMonth", daysInMonth);

        return "search/list-2";

    }

}
