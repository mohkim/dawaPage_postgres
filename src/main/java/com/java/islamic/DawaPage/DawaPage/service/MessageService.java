/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.Message;
import com.java.islamic.DawaPage.DawaPage.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class MessageService {

    @Autowired
    public MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllMessage() {

        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {

        Optional<Message> optn = messageRepository.findById(id);
        return optn.get();
    }

    public String getMessageDetail(Long id) {

        return getMessageById(id).getContent();
    }
      

}
