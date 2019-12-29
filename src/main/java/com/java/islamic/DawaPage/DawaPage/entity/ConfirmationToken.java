/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.entity;

import java.util.UUID;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author FREE_MIND
 */
@Entity
public class ConfirmationToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
   private String confirmationToken;
   private LocalDateTime expiredate;

    @OneToOne(  fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Users user;

    public ConfirmationToken(Users user) {
        this.user = user;
        expiredate= LocalDateTime.now();
      this.expiredate=  expiredate.plusHours(1);    // add 30 for expiration 
        confirmationToken = UUID.randomUUID().toString();
    }
    public void resetToken(){
         expiredate= LocalDateTime.now();
      this.expiredate=    expiredate.plusHours(1);    // add 30 for expiration 
        confirmationToken = UUID.randomUUID().toString(); 
    }

    public ConfirmationToken() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(LocalDateTime expiredate) {
        this.expiredate = expiredate;
    }
 

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
