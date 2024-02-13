package com.example.firstproject.DTO;

public class ArticleForm {
    private String title; //제목 필드
    private String content; //내용 필드

    //생성자

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
