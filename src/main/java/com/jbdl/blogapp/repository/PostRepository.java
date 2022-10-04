package com.jbdl.blogapp.repository;

import com.jbdl.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Does not need @Repository annotation: An implementing class already has it: (SimpleJpaRepository)

}
