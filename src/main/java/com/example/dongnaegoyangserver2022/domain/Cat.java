package com.example.dongnaegoyangserver2022.domain;

import lombok.*;

import javax.persistence.*;

@Getter
//@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat")
@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cat_idx")
    private Long catIdx;

    @Column(length = 100)
    private String name;

    @JoinColumn(name = "user_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
