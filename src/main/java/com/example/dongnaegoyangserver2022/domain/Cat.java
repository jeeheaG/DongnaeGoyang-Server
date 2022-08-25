package com.example.dongnaegoyangserver2022.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
//@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat")
@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catIdx;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private int color;

    @Column(nullable = false)
    private int ear;

    @Column(nullable = false)
    private int tail;

    @Column(nullable = false)
    private int whisker;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private String sido;

    @Column(nullable = false)
    private String gugun;

    @Column(nullable = false)
    private String oftenSeen;

    @Column(length = 100, nullable = false)
    private int sex;

    @Column(length = 100, nullable = false)
    private String age;

    @Column(length = 100, nullable = false)
    private String note;

    @Column(length = 100)
    private String tnr;

    @Column(length = 100)
    private String feed;

    @Column(nullable = false)
    private Boolean isPhoto;

    @Column(nullable = false)
    private LocalDate lastUpdate;

    @JoinColumn(name = "user_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
