package com.jbdl.blogapp.service;

import com.jbdl.blogapp.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

}
