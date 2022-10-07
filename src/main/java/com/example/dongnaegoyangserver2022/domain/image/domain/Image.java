package com.example.dongnaegoyangserver2022.domain.image.domain;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import com.example.dongnaegoyangserver2022.domain.image.dto.ImageResponse;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageIdx;

    @Column(nullable = false)
    private String url;

    @JoinColumn(name = "cat_idx", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cat cat;

    public ImageResponse.ImageSimpleResponse toStringSimpleResponse() {
        return ModelMapperUtil.getModelMapper().map(this, ImageResponse.ImageSimpleResponse.class);
    }
}
