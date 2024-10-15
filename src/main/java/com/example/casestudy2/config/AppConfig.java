package com.example.casestudy2.config;

import com.example.casestudy2.dto.SearchResultDTO;
import com.example.casestudy2.pojo.SearchResult;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<SearchResult, SearchResultDTO>() {
            @Override
            protected void configure() {
                map().setSearchKeywords(source.getSearch().getSearchKeywords());
                map().setPlatform(source.getSearch().getPlatform());
                map().setMatchingPattern(source.getSearch().getMatchingPattern());
                map().setRemarks(source.getSearch().getRemarks());
                map().setSearchDate(source.getSearchDate());
                map().setImgUrl(source.getImgUrl());
                map().setResults(source.getResults());
            }
        });

        return modelMapper;
    }
}
