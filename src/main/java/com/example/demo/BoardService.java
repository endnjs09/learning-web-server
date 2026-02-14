package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService; // 포인트 서비스

    @Transactional
    public void writePost(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        // 게시글 객체
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(member);    // 작성자

        // 게시글 DB에 저장
        boardRepository.save(board);

        // 포인트 적립
        memberService.givePoint(memberId);
    }

    // 모든 게시글을 가져오는 메서드
    public List<Board> findAllPosts() {
        return boardRepository.findAll(); // 리포지토리가 제공하는 findAll()을 호출
    }


    public Board findPostById(Long id) {
        // id로 찾고 없으면 에러를 던지거나 null을 반환함
        return boardRepository.findById(id).orElse(null);
    }


}
