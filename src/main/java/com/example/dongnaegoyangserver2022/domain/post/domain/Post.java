package com.example.dongnaegoyangserver2022.domain.post.domain;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
//@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @JoinColumn(name = "cat_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cat cat;

    @JoinColumn(name = "member_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}