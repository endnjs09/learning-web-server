package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service    // 서비스의 로직
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional  // DB값 변경 시 필요 (하나라도 실패시 롤백)
    public void givePoint(Long id) {
        // DB에서 해당 ID의 회원을 찾아서 꺼내기
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));

        // 점수 10점 올림
        member.setPoints(member.getPoints() + 10);

        // 100점이 넘으면 등급 업그레이드
        if (member.getPoints() >= 100) member.setGrade("SILVER");
    }

    public Member login(String username, String password) {
        return memberRepository.findByUsername(username).filter(m -> m.getPassword().equals(password)).orElse(null);
        // DB(MySQL) 창고를 뒤져봄
        // 아이디로 사람 찾고 -> 비번 맞는지 확인하고 틀리면 null 반환
    }

}
