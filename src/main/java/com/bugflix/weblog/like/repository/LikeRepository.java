package com.bugflix.weblog.like.repository;

import com.bugflix.weblog.like.domain.Like;
import com.bugflix.weblog.like.domain.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Like findLikeById_PostIdAndId_UserId(Long postId, Long userId);

    boolean existsLikeById_PostIdAndId_UserId(Long postId, Long userId);

    Long countByIdPostId(Long postId);
}

