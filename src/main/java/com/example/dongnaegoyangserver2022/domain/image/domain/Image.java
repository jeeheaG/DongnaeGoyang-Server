package com.example.dongnaegoyangserver2022.domain.image.domain;

import com.example.dongnaegoyangserver2022.domain.cat.domain.Cat;
import lombok.*;

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
    private Cat cat;

    public static List<String> toStringList(List<Image> imageList) {
        ArrayList<String> imageStringList = new ArrayList<>();
        for(Image image : imageList){
            imageStringList.add(image.url);
        }
        return imageStringList;
    }
}
