package com.example.casestudy2.controller;

import com.example.casestudy2.dto.SearchDTO;
import com.example.casestudy2.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("search", new SearchDTO());
        return "search/add";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("search") SearchDTO searchDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "search/add";
        }
        searchService.create(searchDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Search criteria saved successfully!");
        return "search/list";
    }
}
