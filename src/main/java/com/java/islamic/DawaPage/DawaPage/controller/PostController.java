/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.entity.Sub_topic;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.service.CommentService;
import com.java.islamic.DawaPage.DawaPage.service.PostService;
import com.java.islamic.DawaPage.DawaPage.service.SubTopicService;
import com.java.islamic.DawaPage.DawaPage.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author KEMAL
 */
@Controller
public class PostController {

    @Autowired
    public PostService postService;
    @Autowired
    public SubTopicService subTopicService;
    @Autowired
    public UserService userService;
    @Autowired
    public CommentService commentService;

    @GetMapping("/postChoice")
    public String getTopicPage(Model model, Principal principal) {
        String email = principal.getName();

        Users user = userService.findByEmail(email);

        List<Sub_topic> pageContent = user.getSub_topicList();
        
        if(!pageContent.isEmpty()){
              model.addAttribute("subTopicList", pageContent);
              return "post/usertopicChoice";
        } else {
              return "post/notopicgiven";
        }
      

    }

    @GetMapping("/postList")
    public String addPostInSubtopic(Model model, @RequestParam String id, Principal principal) {
        
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        
        if (subTopicService.subtopicAccessUser(user, Long.parseLong(id))) {
            
            model.addAttribute("postList", postService.findbySubTopic(Long.parseLong(id)));
            model.addAttribute("subTopic", subTopicService.getSubTopic(Long.parseLong(id)));
            return "post/userpostList";
            
        } else {
                         return "redirect:/postChoice";
        }

    }

//      @GetMapping("/postcedit")
//    public String editTopic(Model model, @RequestParam Long id) {
//
//        model.addAttribute("post", topicService.getTopic(id));
//
//        return "post/postedit";
//
//    }
    @GetMapping("/postNew")
    public String newTopic(Model model, @RequestParam Long id) {
        Post post = new Post();
        post.setSub_topic(subTopicService.getSubTopic(id));

        model.addAttribute("post", post);

        return "post/userpostEdit";

    }

    @GetMapping("/postEdit")
    public String editTopic(Model model,
            @RequestParam Long id,
            Principal principal) {

        model.addAttribute("post", postService.findPost(id));
        model.addAttribute("commentList", commentService.getCommentListByPost(postService.findPost(id)));

        return "post/userpostEdit";

    }

    @GetMapping("/postDelete")
    public String deleteTopic(@RequestParam Long subid, @RequestParam Long id) {

        postService.deletebyId(id);

        return "redirect:/postList?id=" + subid;

    }

    @PostMapping("/postsave")

    public String savePost(Post post, Principal principal, Model model) {
        String email = principal.getName();

        Users user = userService.findByEmail(email);
        post.setUser(user);

        if (post.getId() == null) {
            postService.savePost(post);
        } else {

            Post post2 = postService.findPost(post.getId());
            post.setCreatedDate(post2.getCreatedDate());

            postService.savePost(post);
        }

        return "redirect:/postList?id=" + post.getSub_topic().getId();
    }
    private static final Logger LOG = Logger.getLogger(PostController.class.getName());

    //-------------------------post admin controllers --------------------------------------
    @GetMapping("/postAdminPage")
    public String getPostAdminPage() {
        return "admin/postAdmin/postAdminPage";

    }

    @GetMapping("/usertopic")
    public String getUserTopicRelation(Model model) {

        model.addAttribute("users", userService.getAllUsers());

        return "admin/postAdmin/usertopic";

    }

}
