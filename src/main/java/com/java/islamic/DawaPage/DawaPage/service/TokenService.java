/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.ConfirmationToken;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FREE_MIND
 */
@Repository
public class TokenService {
    
    @Autowired
    private  ConfirmationTokenRepository  confirmationTokenRepository;
    
    
    public  ConfirmationToken    findTokenByUser(Users  user){
           return   confirmationTokenRepository.findByUser(user);
    }
}
