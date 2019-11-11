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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author KEMAL
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty()
    private String name;

    @Column(length = 10000)
    @NotEmpty()
    private String description;

    @OneToMany(mappedBy = "topic")
    private List<Sub_topic> subTopiclist = new ArrayList<Sub_topic>();

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Topic() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 

    @Override
    public String toString() {
        return "Topic{" + "id=" + id + ", name=" + name + ", description=" + description + ", subTopiclist=" + '}';
    }

}
