package com.example.casestudy2.service;

import com.example.casestudy2.dto.SearchDTO;
import com.example.casestudy2.pojo.Search;
import com.example.casestudy2.repo.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private SearchRepository searchRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SearchService searchService;

    private SearchDTO searchDTO;
    private Search searchEntity;

    @BeforeEach
    public void setUp() {
        searchDTO = new SearchDTO();
        searchDTO.setId(1);
        searchDTO.setSearchKeywords("keyword");
        searchDTO.setMatchKeywords("match keyword");
        searchDTO.setPerformanceRewardDays(10);
        searchDTO.setPlatform("Google");
        searchDTO.setMatchingPattern("Partial match");
        searchDTO.setDevice("PC");
        searchDTO.setRemarks("Test remarks");

        searchEntity = new Search();
        searchEntity.setId(1);
        searchEntity.setSearchKeywords("keyword");
        searchEntity.setMatchKeywords("match keyword");
        searchEntity.setPerformanceRewardDays(10);
        searchEntity.setPlatform("Google");
        searchEntity.setMatchingPattern("Partial match");
        searchEntity.setDevice("PC");
        searchEntity.setRemarks("Test remarks");
    }

    @Test
    public void testFindAll() {
        when(searchRepository.findAll()).thenReturn(Arrays.asList(searchEntity));

        List<Search> searchList = searchService.findAll();

        assertNotNull(searchList);
        assertEquals(1, searchList.size());
        assertEquals(searchEntity.getId(), searchList.get(0).getId());

        verify(searchRepository, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        when(modelMapper.map(searchDTO, Search.class)).thenReturn(searchEntity);

        searchService.create(searchDTO);

        verify(searchRepository, times(1)).save(searchEntity);
    }
}
