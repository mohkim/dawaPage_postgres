/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author KEMAL
 */
@Entity
public class Sub_topic {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty()
    @Column(length = 10000)
    private String description;

   
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_topic" )
    private Topic topic;

    public Sub_topic() {
    }

    public Sub_topic(String name, String description) {
        this.name = name;
        this.description = description;

    }

   
 
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Sub_topic{" + "id=" + id + ", name=" + name + ", description=" + description + ", topic=" + '}';
    }
    
    public String  getListString(){
        
        return  topic.getName()+"-" +name;
    }

}
