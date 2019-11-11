/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author KEMAL
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @CreationTimestamp
    public LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    public Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_post")
    public Post post;

    public boolean comment_read;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Comment() {

    }

    public Comment(String content, Post post, Users user) {
        this.content = content;
        comment_read = false;

    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isComment_read() {
        return comment_read;
    }

    public void setComment_read(boolean comment_read) {
        this.comment_read = comment_read;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content=" + content + ", post=" + post.getId() + '}';
    }

     

}
