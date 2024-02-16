package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity  // 클래스가 엔티티 (클래스 필드로 DB에 테이블 생성)
@Getter  //getter 메서드 자동 생성 (각 필드 값을 조회)
@ToString  //toString 메서드 자동 생성 (모든 필드 출력)
@AllArgsConstructor  // 모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor  // 매개변수가 아예 없는 기본 생성자 자동 생성
public class Comment {
    @Id  // 대표키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB가 자동으로 1씩 증가
    private Long id;  // 대표키

    @ManyToOne  // 다대일 관계 (Comment 엔티티와 Article 엔티티)
    @JoinColumn(name="article_id")  // 외래키 생성 (Article 엔티티의 기본키(id)와 매핑)
    private Article article;  // 해당 댓글의 게시물

    @Column  // 해당 필드를 테이블의 속성으로 매핑
    private String nickname;  // 댓글을 단 사용자

    @Column  // 해당 필드를 테이블의 속성으로 매핑
    private String body;  // 댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패 ! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패 ! 게시글의 id가 발못되었습니다.");
        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),  //댓글 아이디
                article,  //게시글
                dto.getNickname(),  // 댓글 닉네임
                dto.getBody()  //댓글 본문
        );
    }
}