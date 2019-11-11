/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Sub_topic;
import com.java.islamic.DawaPage.DawaPage.entity.Topic;
import com.java.islamic.DawaPage.DawaPage.service.SubTopicService;
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
public class SubTopicController {

    public Logger logger = LoggerFactory.getLogger(TopicController.class);
    @Autowired
    public SubTopicService subTopicService;
    @Autowired
    public TopicService topicService;

    @GetMapping("/subtopic")
    public String getTopicPage(Model model, @RequestParam(defaultValue = "-1") Long id) {

        List<Sub_topic> pageContent = subTopicService.getByTopicFK(id);
        model.addAttribute("data", pageContent);

        model.addAttribute("parrentTopic", topicService.getTopic(id));

        return "admin/subtopic";

    }

    @GetMapping("/subtopicedit")
    public String editTopic(Model model, @RequestParam Long sid,
            @RequestParam Long id) {

        model.addAttribute("subtopic", subTopicService.getSubTopic(sid));
        model.addAttribute("parrentTopic", id);

        return "admin/subtopicedit";

    }

    @GetMapping("/subtopicnew")
    public String newTopic(Model model, @RequestParam Long pid) {

        model.addAttribute("subtopic", new Sub_topic());
        model.addAttribute("parrentTopic", pid);

        return "admin/subtopicedit";

    }

    @GetMapping("/subtopicdelete")
    public String deleteTopic(Model model, @RequestParam Long sid, @RequestParam Long id) {
        if (id != null) {
            subTopicService.removeSubTopic(sid);
        }
        return "redirect:/subtopic?id=" + id;

    }

    @PostMapping("/subtopicsave")

    public String saveTopic(@Valid Sub_topic subtopic, @RequestParam Long sid, Model model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("subtopic", subtopic);
            model.addAttribute("parrentTopic", sid);
            return "admin/subtopicedit";
            
        } else if (subTopicService.subtopicExist(subtopic.getName())) {

            model.addAttribute("subtopic", subtopic);
            model.addAttribute("parrentTopic", sid);
            model.addAttribute("exist", true);
            return "admin/subtopicedit";
        } else {

            Topic topic = topicService.getTopic(sid);
            subtopic.setTopic(topic);
            subTopicService.saveSubTopic(subtopic);

            logger.info("kimo2 -----------------> {}", subtopic);

            return "redirect:/subtopic?id=" + sid;
        }
    }

}
