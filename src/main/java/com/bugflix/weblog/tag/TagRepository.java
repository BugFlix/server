package com.bugflix.weblog.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    public Optional<Tag> findTagByTagContent(String tagContent);
}
