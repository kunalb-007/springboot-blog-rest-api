package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        // convert DTO to Entity
        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);

        // convert Entity to DTO
        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        PostDTO response = mapToDTO(post);
        return response;
    }

    @Override
    public PostDTO updatePostById(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(Long id) {
       Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
       postRepository.delete(post);
    }


    // convert Entity to DTO  (helper Method)
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = mapper.map(post, PostDTO.class);      // using ModelMapper
        return postDTO;
    }

    // convert DTO to Entity
    private Post mapToEntity(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);        // using ModelMapper
        return post;
    }

}
