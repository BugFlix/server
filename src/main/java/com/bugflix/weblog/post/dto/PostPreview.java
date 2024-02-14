package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.Post;
import com.bugflix.weblog.tag.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PostPreview {
    private Long postId;
    private String title;
    private List<Tag> tags;
    private Long likeCount;
    private boolean isLike;

    public PostPreview(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
    }
}
