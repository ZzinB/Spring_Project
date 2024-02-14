package com.example.firstproject.DTO;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private Long id; //아이디 필드
    private String title; //제목 필드
    private String content; //내용 필드


    public Article toEntity() {
        return new Article(id, title, content);
    }
}
