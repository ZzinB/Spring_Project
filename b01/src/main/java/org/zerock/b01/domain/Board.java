package org.zerock.b01.domain;

import javax.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false)  //column 길이와 null 허용 여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    //양방향(bidirectiona)참조
    @OneToMany(mappedBy = "board",        //BoardImage의 board 변수
            cascade = {CascadeType.ALL},  //Board 엔티티 객체 모든 상태 변화에 BoardImage 객체도 같이 변경
            fetch = FetchType.LAZY,
            orphanRemoval = true)    //상위 엔티티의 상태 변화 (삭제) 하위 엔티티 삭제까지 진행시켜!
    @Builder.Default
    private Set<BoardImage> imageSet = new HashSet<>();


    //Board 내에서 BoardImage 객체들을 관리

    public void addImage(String uuid, String fileName){
        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(boardImage);
    }

    public void clearImage(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }
}
