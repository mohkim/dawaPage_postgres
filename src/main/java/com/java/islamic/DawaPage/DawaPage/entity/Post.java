package com.java.islamic.DawaPage.DawaPage.entity;

import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author KEMAL
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fk_user")
    private Users user;

    @OneToOne
    @JoinColumn(name = "fk_subTopic")
    private Sub_topic sub_topic;

    private String postTopic;

    @Lob
    private String post_content;
    @OneToMany ( mappedBy = "post")
    private List<Comment> comment_list = new ArrayList<Comment>();

    @UpdateTimestamp
    public LocalDateTime lastUpdatedDate;
    @CreationTimestamp
    public LocalDateTime createdDate;

    private Long visitorsNumber;

    public Long getId() {
        return id;
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

    public Sub_topic getSub_topic() {
        return sub_topic;
    }

    public void setSub_topic(Sub_topic sub_topic) {
        this.sub_topic = sub_topic;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

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

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic;
    }

    public void setComment_list(List<Comment> comment_list) {
        this.comment_list = comment_list;
    }

    public void addComment(Comment comment) {
        comment_list.add(comment);
    }

    public void removeComment(Comment comment) {
        comment_list.remove(comment);
    }

    public Long getVisitorsNumber() {
        return visitorsNumber;
    }

    public void setVisitorsNumber(Long visitorsNumber) {
        this.visitorsNumber = visitorsNumber;
    }

    /*increment no of view*/
    public void incrementViews() {

        setVisitorsNumber(visitorsNumber + 1);

    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", postTopic=" + postTopic + ", comment_list=" + comment_list.size() + '}';
    }

    

}
