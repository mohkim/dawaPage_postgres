/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Topic;
import com.java.islamic.DawaPage.DawaPage.service.TopicService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author KEMAL
 */
@Controller
public class TopicController {

    public Logger logger = LoggerFactory.getLogger(TopicController.class);

    @Autowired
    public TopicService topicService;

    @GetMapping("/topic")
    public String getTopicPage(Model model) {

        List<Topic> pageContent = topicService.getAllTopic();
        model.addAttribute("data", pageContent);

        return "admin/topic";

    }

    @GetMapping("/topicedit")
    public String editTopic(Model model, @RequestParam Long id) {

        model.addAttribute("topic", topicService.getTopic(id));

        return "admin/topicedit";

    }

    @GetMapping("/topicnew")
    public String newTopic(Model model) {

        model.addAttribute("topic", new Topic());

        return "admin/topicedit";

    }

    @GetMapping("/topicdelete")
    public String deleteTopic(Model model, @RequestParam Long id) {

        topicService.removeTopic(id);

        return "redirect:/topic";

    }

    @PostMapping("/topicsave")

    public String saveTopic(@Valid Topic topic, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/topicedit";
        } else if (topicService.topicExist(topic.getName())) {
            model.addAttribute("exist", true);
            return "admin/topicedit";
        } else {
            topicService.saveTopic(topic);

            return "redirect:/topic";
        }
    }
}
