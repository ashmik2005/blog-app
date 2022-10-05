package com.jbdl.blogapp.service.impl;

import com.jbdl.blogapp.entity.Post;
import com.jbdl.blogapp.exception.ResourceNotFoundException;
import com.jbdl.blogapp.payload.PostDto;
import com.jbdl.blogapp.repository.PostRepository;
import com.jbdl.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    @Autowired // Not needed because we have only one member in this class
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // Convert PostDto to our JPA Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        // Convert JPA Entity to DTO (to transfer back to controller)
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        List<PostDto> postDtos =  posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(post);
    }

    // Converts our entity to DTO
    private PostDto mapToDto(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {

        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }
}