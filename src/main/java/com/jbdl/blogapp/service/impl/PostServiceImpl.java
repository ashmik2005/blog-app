package com.jbdl.blogapp.service.impl;

import com.jbdl.blogapp.entity.Post;
import com.jbdl.blogapp.exception.ResourceNotFoundException;
import com.jbdl.blogapp.payload.PostDto;
import com.jbdl.blogapp.payload.PostResponse;
import com.jbdl.blogapp.repository.PostRepository;
import com.jbdl.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PostResponse getAllPosts(int pageNo, int pageSize) {

        // Create a pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);


        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        // Fetch post with given id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Update the post according to the new post-data received in request body
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // Save the updated Post in the database
        Post updatedPost = postRepository.save(post);

        // Return updated Post DTO to controller
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        // Fetch post to be deleted from database
        Post postToDelete = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Delete the post
        postRepository.delete(postToDelete);
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
