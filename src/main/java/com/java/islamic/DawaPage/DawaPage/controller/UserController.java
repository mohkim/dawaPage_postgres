/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Comment;
import com.java.islamic.DawaPage.DawaPage.entity.Post;
import com.java.islamic.DawaPage.DawaPage.entity.Sub_topic;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.service.CommentService;
import com.java.islamic.DawaPage.DawaPage.service.PostService;
import com.java.islamic.DawaPage.DawaPage.service.SubTopicService;
import com.java.islamic.DawaPage.DawaPage.service.UserService;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author KEMAL
 */
@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserService userService;

    @Autowired
    public SubTopicService subTopicService;
    @Autowired
    public PostService postService;
    @Autowired
    public CommentService commentService;

    @GetMapping("/user")
    public String getUserPage(Model model) {

        List<Users> pageContent = userService.findUser_User();
        model.addAttribute("data", pageContent);

        return "user/user";

    }

    @GetMapping("/userdetail")
    public String detailUser(Model model, @RequestParam Long uid) {

        Users user = userService.getUser(uid);

        model.addAttribute("user", user);

        return "user/userdetail";

    }

    @GetMapping("/useredit")
    public String editUser(@RequestParam Long uid, Model model) {
        UserTopicEdit userTopicEdit = new UserTopicEdit();
        userTopicEdit.setId(uid);
        userTopicEdit.setSubTopicid(0L);

        model.addAttribute("allSubTopic", subTopicService.getAllTopic());
        model.addAttribute("userSubTopic", userService.getUser(userTopicEdit.getId()).getSub_topicList());
        model.addAttribute("userTopicEdit", userTopicEdit);

        return "user/useredit";

    }

    @PostMapping("/useraddtopic")
    public String user_add_topic(@ModelAttribute("userTopicEdit") UserTopicEdit userTopicEdit) {

        Sub_topic sub_topic = subTopicService.getSubTopic(userTopicEdit.getSubTopicid());
        Users user = userService.getUser(userTopicEdit.getId());
        List<Sub_topic> list = user.getSub_topicList();
        if (!list.contains(sub_topic)) {
            user.addSub_topic(sub_topic);
        }

        userService.save(user);

        return "redirect:/useredit?uid=" + userTopicEdit.getId();

    }

    @GetMapping("/userRemovetopic")
    public String user_add_topic(@RequestParam Long sid, @RequestParam Long uid) {

        Sub_topic sub_topic = subTopicService.getSubTopic(sid);
        Users user = userService.getUser(uid);
        List<Sub_topic> list = user.getSub_topicList();
        if (list.contains(sub_topic)) {
            user.removeSub_topic(sub_topic);
        }

        userService.save(user);

        return "redirect:/useredit?uid=" + uid;

    }

    @PostMapping("/usersave")
    public String SaveUser(@ModelAttribute("user") Users user) {
        LOG.info("user after -> {}" + user);
        Users user2 = new Users();
        user2 = userService.getUser(user.getUser_id());
        user2.setUserActive(user.isUserActive());
        user2.setExtranote(user.getExtranote());
        userService.save(user2);
        return "redirect:/user";
    }

//     Use  profile  Controllers -----------------------------------------------------------------
    @GetMapping("/UserProfile")
    public String getUserProfile(Model model, Principal principal) {
//
//        Users user = userService.findByEmail(principal.getName());
//        model.addAttribute("user", user);
//        //for password setting 

        ///    return "user/address";
        model.addAttribute("info", true);
        model.addAttribute("info_msg", "User Profile Under Construction !!!!");
        return "user/messageDisplay";

    }

    @PostMapping("/address/save")
    public String SaveUserAddress(@ModelAttribute("user") Users user) {
        Users user1 = userService.getUser(user.getUser_id());
        userService.save(user1);
        return "redirect:/";
    }

    @GetMapping("/password")
    public String getUserPassword(Model model) {
        model.addAttribute("user", new Users());
        return "user/password";
    }

    @PostMapping("/passwordsave")
    public String SaveUserPassword(Model model, @ModelAttribute("user") Users user, Principal principal) {
        LOG.info("Kemal Password Save Controller ");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Users user1 = userService.findByEmail(principal.getName());
        // confirm if he knows the old password
        if (encoder.matches(user.getPassword(), user1.getPassword())) {
            user1.setPassword(encoder.encode(user.getPassword2()));
            userService.save(user1);

            model.addAttribute("info", true);
            model.addAttribute("info_msg", " ኣካውንት ፓስፖርድ ኣዳሊኻ ኣለኻ ብ ዓወት!!!!!");
            return "user/messageDisplay";

        } else {
            model.addAttribute("error", true);

            return "user/password";
        }

    }
    // user  post   controllers below --------------------------------

    @GetMapping("/userpost")
    public String getUserPost(Model model, Principal principal) {

        Users user = userService.findByEmail(principal.getName());
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("userSubTopic", userService.getUser(user.getUser_id()).getSub_topicList());
        model.addAttribute("post", post);

        return "user/userpost";
    }

    @PostMapping("/userpost")
    public String SaveUserPost(Model model, @ModelAttribute("post") Post post) {

        postService.savePost(post);
        model.addAttribute("info", true);
        model.addAttribute("info_msg", " ብዓወት ጽሑፍካ ዓቁብካዮ !!!!!");
        return "user/messageDisplay";
    }

    // ----------------------------------user  activity  part -----------------------------------------------------------
    @GetMapping("/activity")
    public String getUserActivityPage(Model model, Principal principal) {

        Users user = userService.findByEmail(principal.getName());
        List<Comment> commentNotReadList = commentService.getCommentNotRedByUser(user);
        model.addAttribute("comList", commentNotReadList);
        return "user/activity/activity";

    }

}

class UserTopicEdit {

    public Long id;
    public Long subTopicid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubTopicid() {
        return subTopicid;
    }

    public void setSubTopicid(Long subTopicid) {
        this.subTopicid = subTopicid;
    }

}
