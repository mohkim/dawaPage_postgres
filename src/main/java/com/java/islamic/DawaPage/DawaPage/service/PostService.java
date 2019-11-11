/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class PostService {

    @Autowired
    public PostRepository postRepository;
    @Autowired
    public SubTopicService subTopicService;

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findPost(Long id) {

        Optional<Post> post = postRepository.findById(id);
      
        if (post.isPresent()) {
                
            Post post_obj =  post.get();
            if (post_obj.getVisitorsNumber() == null) {
                post_obj.setVisitorsNumber(0L);
            }
            post_obj.incrementViews();   // increment no of views
            savePost(post_obj);

            return post_obj;

        } else {
            return null;
        }
    }
    private static final Logger LOG = Logger.getLogger(PostService.class.getName());

    public List<Post> findbySubTopic(Long id) {
        return postRepository.findbySubTopic(subTopicService.getSubTopic(id));
    }

    /**
     * this method is to find most visited Post in a Page
     */
    public List<Post> findMostVisitedPost() {
        return postRepository.findMostReadPost();

    }

    /**
     * This method is to find most new Post
     */
    public List<Post> findLatestPost() {
        return postRepository.findLatestPost();

    }

    public void deletebyId(Long id) {
        postRepository.deleteById(id);
    }

}
