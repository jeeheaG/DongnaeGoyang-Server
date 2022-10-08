package com.example.dongnaegoyangserver2022.domain.notice.dao;

import com.example.dongnaegoyangserver2022.domain.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAll(Pageable pageable); //OrderByCreatedTimeDesc
}
