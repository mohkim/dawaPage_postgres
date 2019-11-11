/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author KEMAL
 */
 
public  interface  MessageRepository extends  JpaRepository<Message, Long> {
    
     
}
