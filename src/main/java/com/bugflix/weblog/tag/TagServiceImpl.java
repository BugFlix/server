package com.bugflix.weblog.tag;

import com.bugflix.weblog.tag.dto.TagRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private static TagRepository tagRepository;

    public List<Tag> findTagsByPostId(Long postId){
        return tagRepository.findTagsByPostPostId(postId);
    }
}
