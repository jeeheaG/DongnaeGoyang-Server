package com.example.dongnaegoyangserver2022.domain.notice.api;

import com.example.dongnaegoyangserver2022.domain.notice.application.NoticeService;
import com.example.dongnaegoyangserver2022.domain.notice.dto.NoticeResponse;
import com.example.dongnaegoyangserver2022.global.common.JsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/v1/notices")
    public ResponseEntity<Object> getNoticeList(@RequestParam int page){
        log.info("[API] getNoticeList");
        Sort sort = Sort.by("modifiedTime").descending();
        PageRequest pageRequest = PageRequest.of(page, 30, sort);
        NoticeResponse.GetNoticeListResponseContainer noticeResponseContainer = noticeService.getNoticeList(pageRequest);
        return ResponseEntity.ok(new JsonResponse(200, "success getNoticeList", noticeResponseContainer));
    }

}
