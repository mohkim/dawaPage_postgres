/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Comment;
import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
 @Repository
@Transactional
public  interface CommentRepository extends  JpaRepository<Comment, Long> {
    
    
     @Query(value="SELECT * FROM comment  where  fk_post=?",nativeQuery = true)
    public  List<Comment>    findCommentListbyPost(Long id);
    
     @Query(value="SELECT * FROM comment  where  fk_user=? and  content=?",nativeQuery = true)
    public  Comment    findbyUserAndComment(Long id,String content);
    
    
//     @Query(value="SELECT c FROM  Comment  c  where c.comment_read=0 and c.user=?1 and c.comment_read=false")
       @Query(value="SELECT c FROM  Comment  c  where  c.user=?1 and c.comment_read=0 ")
 
    public  List<Comment>    findCommentNotRedByUser(Users  user);
}
