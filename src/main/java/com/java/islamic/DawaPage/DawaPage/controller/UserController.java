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
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    public Logger logger = LoggerFactory.getLogger(UserController.class);
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
        logger.info("user after -> {}", user);
        Users user2 = new Users();
        user2 = userService.getUser(user.getUser_id());
        user2.setUserActive(user.isUserActive());
        user2.setExtranote(user.getExtranote());
        userService.save(user2);
        return "redirect:/user";
    }

    @GetMapping("/registerform")
    public String getRegistrationForm(Model model) {

        model.addAttribute("user", new Users());

        return "user/registerform";
    }

    @PostMapping("/registerformSave")
    public String saveRegistrationForm(@Valid Users user, Model model, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "user/registerform";
        } else if (userService.isEmailPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "user/registerform";
        }
        Users u = userService.findByEmail(principal.getName());
        user.setCreatedBy(u.getUser_id());  // 
        //user.dbBithDate();          
        userService.newUser(user);

        return "user/user_success";

    }

//     Use  profile  Controllers -----------------------------------------------------------------
    @GetMapping("/address")
    public String getUserProfile(Model model, Principal principal) {

        Users user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        //for password setting 

        return "user/address";
    }

    @PostMapping("/address/save")
    public String SaveUserAddress(@ModelAttribute("user") Users user) {
        Users user1 = userService.getUser(user.getUser_id());
        user1.setCountry(user.getCountry());
        user1.setTown(user.getTown());
        user1.setPhone_Number(user.getPhone_Number());

        userService.save(user1);

        return "redirect:/";
    }

    @GetMapping("/password")
    public String getUserPassword(Model model, Principal principal) {

        Users user = userService.findByEmail(principal.getName());
        user.setPassword("");

        model.addAttribute("user", user);
        model.addAttribute("password2", "");
        model.addAttribute("error", false);

        //for password setting 
        return "user/password";
    }

    @PostMapping("/passwordsave")
    public String SaveUserPassword(Model model, @ModelAttribute("user") Users user, @ModelAttribute("password2") String password2) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Users user1 = userService.getUser(user.getUser_id());
        logger.info("new Password -- > {}", user.getPassword2());

        // confirm if he knows the old password
        if (encoder.matches(user.getPassword(), user1.getPassword())) {
            user1.setPassword(encoder.encode(user.getPassword2()));
            userService.save(user1);
            return "user/success";

        } else {
            model.addAttribute("error", true);

            //for password setting 
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
    public String SaveUserPost(@ModelAttribute("post") Post post) {

        postService.savePost(post);
        return "user/success";
    }

    // ----------------------------------user  activity  part -----------------------------------------------------------
    @GetMapping("/activity")
    public String getUserActivityPage(Model model, Principal principal) {

        Users user = userService.findByEmail(principal.getName());
        List<Comment> commentNotReadList = commentService.getCommentNotRedByUser(user);

        model.addAttribute("comList", commentNotReadList);

        return "user/activity/activity";

    }
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(UserController.class.getName());

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
