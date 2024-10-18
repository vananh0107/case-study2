package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchDTO;
import com.example.casestudy2.pojo.Search;
import com.example.casestudy2.repo.SearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Search> findAll(){
        return searchRepository.findAll();
    }

    public void create(SearchDTO searchDTO){
        searchRepository.save(modelMapper.map(searchDTO, Search.class));
    }

}
