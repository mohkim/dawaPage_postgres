/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author KEMAL
 */
@Entity

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Email
    private String email;

    private String password;
    @Transient
    private String password2;

 
    @NotEmpty
    private String name;
    private boolean userActive;

    @Length(max = 1000)
    private String extranote;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Sub_topic> sub_topicList = new ArrayList<Sub_topic>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles = new HashSet<>();

    @UpdateTimestamp
    public LocalDateTime lastUpdatedDate;
    @CreationTimestamp
    public LocalDateTime createdDate;

    private Long createdBy;

    public Users() {
        this.userActive = false;
    }

    public String getExtranote() {
        return extranote;
    }

    public void setExtranote(String extranote) {
        this.extranote = extranote;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void addRoles(Roles role) {
        roles.add(role);
    }

    public void removeRoles(Roles role) {
        roles.remove(role);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Sub_topic> getSub_topicList() {
        return sub_topicList;
    }

    public void addSub_topic(Sub_topic sub_topic) {
        this.sub_topicList.add(sub_topic);
    }

    public void removeSub_topic(Sub_topic sub_topic) {
        this.sub_topicList.remove(sub_topic);
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Users{" + "user_id=" + user_id + ", email=" + email + ", password=" + password + ", password2=" + password2 + ", name=" + name + ", userActive=" + userActive + '}';
    }

    public String getBCryptPassword( ) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return (encoder.encode(this.password));
    }

    public String getFullName() {
        return name;
    }

}
