package com.jbdl.blogapp.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title should have more than one word")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Please include a more detailed description")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
