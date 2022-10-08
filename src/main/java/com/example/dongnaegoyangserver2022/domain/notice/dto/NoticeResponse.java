package com.example.dongnaegoyangserver2022.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeResponse {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetNoticeListResponseContainer {
        private List<GetNoticeListResponse> noticeList;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class GetNoticeListResponse {
        private Long noticeIdx;
        private String title;
        private String content;
        private LocalDateTime modifiedTime;
    }
}
