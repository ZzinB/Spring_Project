package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardImageDTO;
import org.zerock.b01.dto.BoardListAllDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;  //Q도메인 객체
        JPQLQuery<Board> query = from(board);  //select ..from board
        query.where(board.title.contains("1"));  //where title like ..

        //paging
        this.getQuerydsl().applyPagination(pageable, query); //limit ..

        List<Board> list = query.fetch();  //fetch() : JPQLQuery 실행

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        //검색조건에 keyword가 있다면
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {  //(
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                }
            } //end for
            query.where(booleanBuilder);
        }  //end if
        //bno > 0
        query.where(board.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board)); //left join

        query.groupBy(board);

        //검색 조건
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {  //(
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                }
            } //end for
            query.where(booleanBuilder);
        }  //end if
        //bno > 0
        query.where(board.bno.gt(0L));


        //Projection: JPQL의 결과를 DTO로 처리
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,
                board.title,
                board.writer,
                board.regDate,
                reply.count().as("replyCount")
        ));
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        Long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }

    //Boardm BoardImage를 한번에 처리하고 댓글 개수 count
    @Override
    public Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));  //left join

        //검색 조건
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {  //(
                switch (type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            } //end for
            boardJPQLQuery.where(booleanBuilder);
        }

        boardJPQLQuery.groupBy(board);

        getQuerydsl().applyPagination(pageable, boardJPQLQuery);  //paging

        JPQLQuery<Tuple> tupleJPQLQuery = boardJPQLQuery.select(board, reply.countDistinct());


        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        //List<Tuple>의 결과 tupleList를 List<BoardListAllDTO>로 변경
        List<BoardListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Board board1 = (Board) tuple.get(board);
            long replyCount = tuple.get(1, Long.class);

            BoardListAllDTO dto = BoardListAllDTO.builder()
                    .bno(board1.getBno())
                    .title(board1.getTitle())
                    .writer(board1.getWriter())
                    .regDate(board1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            //BoardImage를 BoardImageDTO 처리
            List<BoardImageDTO> imageDTOS = board1.getImageSet().stream().sorted().map(boardImage -> BoardImageDTO.builder()
                    .uuid(boardImage.getUuid())
                    .fileName(boardImage.getFileName())
                    .ord(boardImage.getOrd())
                    .build()
                    ).collect(Collectors.toList());

            dto.setBoardImages(imageDTOS);  //처리된 BoardImageDTO들을 추가

            return dto;
        }).collect(Collectors.toList());
        long totalCount = boardJPQLQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}

