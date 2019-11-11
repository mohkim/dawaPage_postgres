package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Message;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.service.MessageService;
import com.java.islamic.DawaPage.DawaPage.service.UserService;
import java.security.Principal;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author KEMAL
 */
@Controller
public class MessageController {

    @Autowired
    public UserService userService;
    @Autowired

    public MessageService messageService;
    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

    @GetMapping("/contact2")
    public String getContactPage(Model model, Principal principal) {
        Users user = userService.findByEmail(principal.getName());

        model.addAttribute("name", user.getFullName());
        model.addAttribute("message", new Message());
        return "user/contact";
    }

    @GetMapping("/contact")
    public String getContactPage() {

        return "home/contact";
    }

    @PostMapping("/messageSave")
    public String SaveContactMessage(Message message, Principal principal) {
        Users user = userService.findByEmail(principal.getName());
        message.setUser(user);
        messageService.saveMessage(message);
        return "user/messagesent";
    }
    //------------------------------------------message inbox for admin  ---------------------------------------------

    @GetMapping("/messageInbox")
    public String getAdminMessageInbox(Model model) {

        model.addAttribute("allmessages", messageService.getAllMessage());

        return "admin/messageInbox";
    }

    @GetMapping("/messageDetail")
    @ResponseBody
    public String getMessageDetail(Long id) {

        return messageService.getMessageDetail(id);

    }
    
    
    @GetMapping("/messageDelete")
   
    public String DeleteMessage(Long id) {

         messageService.deleteMessageById(id);
        
        return  "redirect:/messageInbox";

    }

}
