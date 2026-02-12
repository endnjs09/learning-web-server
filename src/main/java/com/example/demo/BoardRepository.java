package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// 게시글을 DB에 저장
public interface BoardRepository extends JpaRepository<Board, Long> {
}

