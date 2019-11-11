/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */

public  interface TopicRepository extends  JpaRepository<Topic, Long> {
 
    
    public  Topic  findByName(String name);
}
