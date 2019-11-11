/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.entity.Sub_topic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
 
public  interface PostRepository extends  JpaRepository<Post, Long> {
    
     @Query("SELECT p FROM Post p  where  p.sub_topic=?1")
    public  List<Post>    findbySubTopic(Sub_topic sub_topic);
    
     
      @Query(value = "SELECT * FROM Post  p  order by created_date  desc  limit  10", nativeQuery = true)
    public  List<Post>   findLatestPost();
    
     @Query(value = "SELECT * FROM post  order by visitors_number  desc  limit  10", nativeQuery = true)
    public  List<Post>   findMostReadPost();
    
     
}
