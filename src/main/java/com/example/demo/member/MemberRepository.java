package com.example.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Member 테이블을 관리
// JpaRepository를 상속받으면 save(), findById(), findAll() 같은 기능을 얻음
public interface MemberRepository extends JpaRepository<Member, Long> {

    // SELECT * FROM member WHERE username = ?
    Optional<Member> findByUsername(String username);

    // 해당 이름을 가진 회원이 이미 있는지 여부를 확인
    boolean existsByUsername(String username);
}

// 아무런 코드를 안 써도 기본적인 저장(save), 조회(findById) 기능을 스프링이 다 만들어줌