/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.ConfirmationToken;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.repository.ConfirmationTokenRepository;
import com.java.islamic.DawaPage.DawaPage.service.EmailSenderService;
import com.java.islamic.DawaPage.DawaPage.service.RoleService;
import com.java.islamic.DawaPage.DawaPage.service.UserService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author FREE_MIND
 */
@Controller
public class UserAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RoleService roleService;

    public final String url_domain = "http://localhost:8080";
   // public final String url_domain="https://dawapage.herokuapp.com";
    private static final Logger LOG = Logger.getLogger(UserAccountController.class.getName());

    @GetMapping("/registerform")
    public String displayRegistration(Model model, Users user) {
        model.addAttribute("user", new Users());
        return "user/registerform";

    }

    @PostMapping(value = "/registerformSave")
    public String registerUser(Model model, Users user,
            RedirectAttributes redirect, Principal principal) {

        Users existingUser = userService.findByEmail(user.getEmail());
        Long cretor_usr = userService.findByEmail(principal.getName()).getUser_id();
        if (existingUser != null) {
            redirect.addFlashAttribute("exist", true);
            redirect.addAttribute("user", user);
            return "redirect:/registerform";
        } else {
            user.setCreatedBy(cretor_usr);
            userService.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            sendEmailConfirmation(confirmationToken, user, userService.findUserById(user.getCreatedBy()));

            model.addAttribute("info", true);
            model.addAttribute("info_msg", " ናብ ዝስዕብ ኢመይል መልአኽቲ ተሰዲዱ ኣሎ    :" + user.getEmail() + "   !!!!!");
            return "user/messageDisplay";
        }

    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {

            if (token.getExpiredate().isBefore(LocalDateTime.now())) {
                model.addAttribute("error", true);
                model.addAttribute("error_msg", "ግዚኡ ዘሕለፈ  መልአኽቲ !!!!!");
                return "user/messageDisplay";
            }
            Users user = userService.findByEmail(token.getUser().getEmail());
            user.setUserActive(true);
            userService.save(user);

            model.addAttribute("user", user);
            return "user/setpassword";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("error_msg", "ዘይቕቡል መልአኽቲ ወይ ዝተበላሸወ መልአኽቲ !!!!!");
            return "user/messageDisplay";
        }

    }

    @PostMapping("/newpasswordsave")
    public String saveNewPasswordForUser(Model model, Users user) {
        LOG.info(user.toString());

        Users usr = userService.findUserById(user.getUser_id());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        usr.setPassword(encoder.encode(user.getPassword()));   // set Password
        usr.setUserActive(true);                                                               // set User Enabled
        usr.addRoles(roleService.getUserRole());
        userService.save(usr);

        model.addAttribute("info", true);
        model.addAttribute("info_msg", "ሓዲሽ ፓስዎርት ኣብ ምቃም ተዓዊትካ ኣለኻ !!!!!");
        return "user/messageDisplay";

    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("usr", new Users());
        return "user/forgotPassword";
    }

    @PostMapping("/forgotPasswordsave")
    public String forgotPasswordForm(Model model, Users usr) {
        LOG.info(usr.toString());
        ConfirmationToken ct = null;
        Users user = userService.findByEmail(usr.getEmail());
        if (user != null) {
            ct = confirmationTokenRepository.findByUser(user);
            if (ct != null) {
                ct.resetToken();
            } else {  // no token related to the user 
                ct = new ConfirmationToken(user);

            }

            passwordRecover(ct, user);

            model.addAttribute("emailId", user.getEmail());

            model.addAttribute("info", true);
            model.addAttribute("info_msg", " ናብ ዝስዕብ ኢመይል መልአኽቲ ተሰዲዱ ኣሎ    :" + user.getEmail() + "   !!!!!");
            return "user/messageDisplay";
        } else {
            model.addAttribute("error", true);
            model.addAttribute("error_msg", "በዚ ኢመይል ኣባል የብልናን  !!!!!");
            return "user/messageDisplay";
        }

    }

    private void sendEmailConfirmation(ConfirmationToken ct, Users user, Users o_user) {
        confirmationTokenRepository.save(ct);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
         mailMessage.setFrom("dawa.age2020@gmail.com");
        mailMessage.setText(getNewUserMessage(ct.getConfirmationToken(), user.getName(), o_user.getName()));

        emailSenderService.sendEmail(mailMessage);
    }

    private void passwordRecover(ConfirmationToken ct, Users user) {
        confirmationTokenRepository.save(ct);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Forgot Password!");
        mailMessage.setFrom("dawa.age2020@gmail.com");
        mailMessage.setText( getMessageForgotPassword(ct.getConfirmationToken(), user.getName()));

        emailSenderService.sendEmail(mailMessage);
    }

    public String getMessageForgotPassword(String token_string, String user_n) {
        String message = "Dear " + user_n + "\n"
                + "You have asked to reset your password becouse you forgot it . please click link below to  \n"
                + "reset password for your account : \n"
                + url_domain + "/confirm-account?token=" + token_string;

                  return message;
    }
    public String getNewUserMessage(String token_string, String user_n, String user_o) {
        String message = "Dear " + user_n + "\n"
                + "You have been recommended to join  DawaPage  community . Mr  " + user_o + " have registerd you next step \n"
                + "is to set password for your account  using link below \n"
                + url_domain + "/confirm-account?token=" + token_string;

                  return message;
    }
}
