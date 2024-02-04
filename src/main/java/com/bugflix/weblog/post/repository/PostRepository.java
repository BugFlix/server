package com.bugflix.weblog.post.repository;

import com.bugflix.weblog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPageUrl(String url);

    List<Post> findByPageUrlAndUserUserId(String url, Long userId);

}
