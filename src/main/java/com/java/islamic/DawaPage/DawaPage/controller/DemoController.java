/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.controller;

import com.java.islamic.DawaPage.DawaPage.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author FREE_MIND
 */
@Controller
public class DemoController {

    @GetMapping("/messageDisplay")
    public String getUserPassword(Model model) {
        model.addAttribute("info", true);
        model.addAttribute("info_msg", "sample Info message !!!!!");
        return "user/messageDisplay";
    }

    @GetMapping("/messageDisplay2")
    public String getUserPassword2(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("error_msg", "sample Error message !!!!!");
        return "user/messageDisplay";
    }

}
