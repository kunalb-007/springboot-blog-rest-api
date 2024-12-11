package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long id);

    PostDTO updatePostById(PostDTO postDTO, Long id);

    void deletePostById(Long id);
}
