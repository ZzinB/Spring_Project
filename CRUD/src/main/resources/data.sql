-- 기존 데이터
INSERT INTO ARTICLE(title, content) VALUES ('가', '11');
INSERT INTO ARTICLE(title, content) VALUES ('나', '22');
INSERT INTO ARTICLE(title, content) VALUES ('다', '33');

-- article 테이블에 데이터 추가
INSERT INTO article(title, content) VALUES ('인생 영화는?', '댓글 ㄱ');
INSERT INTO article(title, content) VALUES ('소울 푸드는?', '댓글 ㄱ ㄱ');
INSERT INTO article(title, content) VALUES ('취미는?', '댓글 ㄱ ㄱ ㄱ');

-- 4번 글에 댓글
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Park', '영화 안봄');
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Kim', '웡카');
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Choi', '포레스트 검프!');

-- 5번 글에 댓글
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Park', '빵');
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Kim', '초밥');
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Choi', '샤브샤브');

-- 6번 글에 댓글
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Park', '영화보기');
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Kim', '책읽기');
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Choi', '코딩!');

