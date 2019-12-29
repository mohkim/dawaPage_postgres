package com.java.islamic.DawaPage.DawaPage;

import com.java.islamic.DawaPage.DawaPage.service.RoleService;
import com.java.islamic.DawaPage.DawaPage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import java.util.List;
import com.java.islamic.DawaPage.DawaPage.entity.Users;

@SpringBootApplication
@Controller
public class DawaPageApplication implements CommandLineRunner {

    @Autowired
    public RoleService roleService;
    @Autowired
    public UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DawaPageApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Users> lst_adm = userService.findUser_Admin();

        if (lst_adm.isEmpty()) {
            creteAdmin();
        }

    }

    private void creteAdmin() {
        Users amd = new Users();
        amd.setEmail("kk@kk.com");
        amd.setPassword("kk");
        amd.setName("Kemal Mohammed");
        amd.setUserActive(true);
        userService.newAdmin(amd);
    }
}
