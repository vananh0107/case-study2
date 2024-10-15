package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.repo.SearchResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResultService {
    @Autowired
    private SearchResultRepository searchResultRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<SearchResultDTO> findAll(){
        return searchResultRepository.findAll()
                .stream().map(searchResult -> modelMapper.map(searchResult,SearchResultDTO.class))
                .toList();
    }
    public List<SearchResultDTO> getResultsByYearAndMonth(int year, int month) {
        return searchResultRepository.findByYearAndMonth(year, month)
                .stream().map(searchResult -> modelMapper.map(searchResult,SearchResultDTO.class))
                .toList();
    }
}
