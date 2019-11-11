/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.Comment;
import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class CommentService {
    @Autowired
    public  CommentRepository commentRepository;
    
    
    
    public void  saveComent(Comment  comment){
         commentRepository.save(comment);
    }
    public void  deleteById(Long   id){
         commentRepository.deleteById(id);
    }
    public List<Comment>  getCommentListByPost(Post  post){
        return commentRepository.findCommentListbyPost(post.getId()); 
    }
    
    public  Comment   getCommentByUserAndContent(Users user,String content){
      
        return commentRepository.findbyUserAndComment(user.getUser_id(),content) ;                   
        
       
    }
     public List<Comment>   getCommentNotRedByUser(Users user){
      
        return  commentRepository.findCommentNotRedByUser(user);
        
       
    }
    
}
