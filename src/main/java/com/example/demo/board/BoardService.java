package com.example.demo.board;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 주소창에 억지로 삭제 주소를 치고 들어오는 걸 막기 위해 한 번 더 검증
    @Transactional
    public void deletePost(Long postId, Long currentUserId) {
        // 삭제할 글이 있는지 확인
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // [핵심 보안] 글쓴이 ID와 현재 접속자 ID가 같은지 검증
        if (!board.getWriter().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("본인이 작성한 글만 삭제할 수 있습니다.");
        }

        // 삭제 실행
        boardRepository.delete(board);
    }

    @Transactional
    public void updatePost(Long postId, String title, String content, Long currentUserId) {
        // 기존 글 가져오기
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("글이 없습니다."));

        // 권한 확인 (삭제와 동일)
        if (!board.getWriter().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 데이터 변경 (여기서 값을 바꾸면 메서드 종료 시 DB에 자동 반영됨)
        board.setTitle(title);
        board.setContent(content);
    }

    public Page<Board> getList(int page) {

        // 요청한 페이지 번호, 한 페이지당 개수, 정렬 기준
        // page: 0부터 시작 / 10: 10개씩 / Sort: id 필드 기준 내림차순(DESC) -> 최신글이 위로 오게
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return boardRepository.findAll(pageable);
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
