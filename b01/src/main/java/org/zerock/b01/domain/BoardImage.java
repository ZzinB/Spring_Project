package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
//@OneTOMany처리에서 순번에 맞게 정렬하기 위해 : Comparable
public class BoardImage implements Comparable<BoardImage>{

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage o) {
        return this.ord - o.ord;
    }

    //양방향(bidirectiona)참조
    public void changeBoard(Board board){
        this.board = board;
    }
}
