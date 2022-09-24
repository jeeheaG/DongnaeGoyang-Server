package com.example.dongnaegoyangserver2022.domain.cat.application;

import com.example.dongnaegoyangserver2022.domain.cat.dto.CatRequest;
import com.example.dongnaegoyangserver2022.domain.cat.dao.CatRepository;
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
