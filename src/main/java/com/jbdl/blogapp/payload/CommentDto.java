package com.jbdl.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty(message = "Name cannot be an empty field")
    private String name;

    @NotEmpty
    @Email(message = "Email entered is invalid")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body is too small")
    private String body;

}
