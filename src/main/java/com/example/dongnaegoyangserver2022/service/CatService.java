package com.example.dongnaegoyangserver2022.service;

import com.example.dongnaegoyangserver2022.dto.CatRequest;
import com.example.dongnaegoyangserver2022.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatService {
    public final CatRepository catRepository;

    public void addCat(CatRequest.createRequest request){
        // 내용...
    }

}
