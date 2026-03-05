package com.example.demo.board;

import com.example.demo.board.dto.BoardRequest;
import com.example.demo.board.dto.BoardResponse;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardAPIControl {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    // 목록 조회 API (페이징 포함)
    @GetMapping("/list")
    public ResponseEntity<Page<BoardResponse>> getList(@RequestParam(value="page", defaultValue="0") int page) {
        Page<Board> paging = boardService.getList(page);

        // 엔티티 Page를 DTO Page로 변환 (중요!)
        Page<BoardResponse> response = paging.map(BoardResponse::new);

        return ResponseEntity.ok(response);
    }


    // 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getDetail(@PathVariable("id") Long id) {
        Board post = boardService.findPostById(id);
        if (post == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new BoardResponse(post));
    }

    // 글쓰기
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody BoardRequest request, HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return ResponseEntity.status(401).body("로그인이 필요한 서비스입니다.");
        }

        // 글 쓰기 및 포인트 적립 실행 (DB 업데이트)
        boardService.writePost(loginMember.getId(), request.getTitle(), request.getContent());

        // DB에서 최신 정보(점수/등급 반영된) 다시 가져오기
        Member updatedMember = memberRepository.findById(loginMember.getId()).orElse(null);

        // 세션 정보 업데이트 (최신 버전으로 교체)
        session.setAttribute("loginMember", updatedMember);

        return ResponseEntity.ok("게시글이 등록되었습니다.");
    }

    // 수정 실행 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id,
                                         @RequestBody BoardRequest request,
                                         HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) return ResponseEntity.status(401).body("권한이 없습니다.");

        try {
            boardService.updatePost(id, request.getTitle(), request.getContent(), loginMember.getId());
            return ResponseEntity.ok("수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // 삭제 실행 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) return ResponseEntity.status(401).body("권한이 없습니다.");

        try {
            boardService.deletePost(id, loginMember.getId());
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
