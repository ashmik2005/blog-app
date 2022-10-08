package com.jbdl.blogapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY) // Tells Hibernate to only fetch the related entities from the database when you use the relationship
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
