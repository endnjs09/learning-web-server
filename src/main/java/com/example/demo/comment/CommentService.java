package com.example.demo.comment;

import com.example.demo.board.Board;
import com.example.demo.board.BoardRepository;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void writeComment(Long boardId, Long memberId, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBoard(board);
        comment.setWriter(member);

        commentRepository.save(comment);
    }
}
