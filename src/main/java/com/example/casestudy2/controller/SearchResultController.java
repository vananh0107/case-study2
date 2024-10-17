package com.example.casestudy2.controller;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.service.SearchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchResultController {
    @Autowired
    private SearchResultService searchResultService;

    @GetMapping("/list")
    public String showSearchResults(@RequestParam(value = "monthYear", required = false) String monthYear, Model model) {
        if (monthYear == null || monthYear.isEmpty()) {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M");
            monthYear = now.format(formatter);
        }
        String[] parts = monthYear.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        List<SearchResultDTO> results = searchResultService.getResultsByYearAndMonth(month, year);

        model.addAttribute("resultsByDay", results);
        model.addAttribute("selectedMonthYear", monthYear);
        model.addAttribute("daysInMonth", daysInMonth);
        return "search/list";
    }
}
