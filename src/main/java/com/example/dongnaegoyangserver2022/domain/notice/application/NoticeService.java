package com.example.dongnaegoyangserver2022.domain.notice.application;

import com.example.dongnaegoyangserver2022.domain.notice.dao.NoticeRepository;
import com.example.dongnaegoyangserver2022.domain.notice.domain.Notice;
import com.example.dongnaegoyangserver2022.domain.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeResponse.GetNoticeListResponseContainer getNoticeList(Pageable pageable){
        Page<Notice> noticePage = noticeRepository.findAll(pageable); //OrderByCreatedTimeDesc

        List<Notice> noticeList = noticePage.getContent();
        List<NoticeResponse.GetNoticeListResponse> noticeListResponses = noticeList
                .stream()
                .map(notice ->
                        notice.toGetNoticeListResponse())
                .collect(Collectors.toList());

        return NoticeResponse.GetNoticeListResponseContainer.builder()
                .noticeList(noticeListResponses)
                .build();
    }
}
