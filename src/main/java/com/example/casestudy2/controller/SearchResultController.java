package com.example.casestudy2.controller;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.service.SearchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchResultController {
    @Autowired
    private SearchResultService searchResultService;

    @GetMapping("/list")
    public String showSearchResults(@RequestParam(value = "monthYear", defaultValue = "2024-10") String monthYear, Model model) {
        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        List<SearchResultDTO> allResults = searchResultService.getResultsByYearAndMonth(year, month);

        List<List<SearchResultDTO>> resultsByDay = new ArrayList<>();

        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);

            List<SearchResultDTO> resultsForDay = allResults.stream()
                    .filter(result -> result.getSearchDate().isEqual(currentDate))
                    .collect(Collectors.toList());

            resultsByDay.add(resultsForDay);
        }

        model.addAttribute("resultsByDay", resultsByDay);
        model.addAttribute("selectedMonthYear", monthYear);
        model.addAttribute("daysInMonth", daysInMonth);

        return "search/list-1";
    }
    @GetMapping("/list-2")
    public String showSearchResultsDetail(@RequestParam(value = "monthYear", defaultValue = "2024-10") String monthYear, Model model) {
        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        List<SearchResultDTO> allResults = searchResultService.findAll();

        List<List<SearchResultDTO>> resultsByDay = new ArrayList<>();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = LocalDate.of(year, month, day);

            List<SearchResultDTO> resultsForDay = allResults.stream()
                    .filter(result -> result.getSearchDate().isEqual(currentDate))
                    .collect(Collectors.toList());

            resultsByDay.add(resultsForDay);
        }

        model.addAttribute("resultsByDay", resultsByDay);
        model.addAttribute("selectedMonthYear", monthYear);
        model.addAttribute("daysInMonth", daysInMonth);

        return "search/list-2";
    }
}
