/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

 
import com.java.islamic.DawaPage.DawaPage.entity.Topic;
import com.java.islamic.DawaPage.DawaPage.repository.TopicRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class TopicService {
    @Autowired
    public  TopicRepository    topicRepository;

    public TopicService() {
    }
    
    
    public  Topic   getTopic(Long  id){
        return   (topicRepository.findById(id)).get();
    }
    public   void    saveTopic(Topic  topic){
            topicRepository.save(topic);
    } 
    public   void    removeTopic(Topic  topic){
            topicRepository.delete(topic);
    } 
      public   void    removeTopic(Long id){
            topicRepository.deleteById(id);
    } 
    public  List<Topic>   getAllTopic(){
        return  topicRepository.findAll();
    }

    public boolean topicExist(String name) {
        if(topicRepository.findByName(name)==null)
            return   false;
        else  return  true;
    }
}
