/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Comment;
import com.java.islamic.DawaPage.DawaPage.entity.Sub_topic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
public interface SubTopicRepository extends JpaRepository<Sub_topic, Long> {

    public Sub_topic findByName(String name);

    @Query(value = "SELECT * FROM sub_topic WHERE fk_topic = ?1", nativeQuery = true)
    public List<Sub_topic> findByfkTopic(Long id);
    
  
}
