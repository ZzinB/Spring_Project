package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  //해당 클래스를 JPA와 연동해 테스팅
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId(){
        /* Case 1: 4번 게시글의 모든 댓글 조회 */ {
        // 1. 입력 데이터 준비
        Long articleId = 4L;
        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 3. 예상 데이터
        Article article = new Article(4L, "인생 영화는?", "댓글 ㄱ");
        Comment a = new Comment(1L, article, "Park", "영화 안봄");
        Comment b = new Comment(2L, article, "Kim", "웡카");
        Comment c = new Comment(3L, article, "Choi", "포레스트 검프!");
        List<Comment> expected = Arrays.asList(a, b, c);
        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

    /* Case 2: 1번 게시글의 모든 댓글 조회 */ {
        // 1. 입력 데이터 준비
        Long articleId = 1L;
        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 3. 예상 데이터
        Article article = new Article(1L, "가", "11");
        List<Comment> expected = Arrays.asList();
        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }
    }
    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "Park";
            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "인생 영화는?", "댓글 ㄱ"), nickname, "영화 안봄");
            Comment b = new Comment(4L, new Article(5L, "소울 푸드는?", "댓글 ㄱ ㄱ"), nickname, "빵");
            Comment c = new Comment(7L, new Article(6L, "취미는?", "댓글 ㄱ ㄱ ㄱ"), nickname, "영화보기");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }
    }
}