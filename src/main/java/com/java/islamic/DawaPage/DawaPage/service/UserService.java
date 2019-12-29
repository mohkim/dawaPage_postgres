/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.Roles;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import com.java.islamic.DawaPage.DawaPage.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class UserService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleService roleService;

    public void newAdmin(Users user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        user.addRoles(roleService.getAdminRole());
        userRepository.save(user);

    }

    public Users findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void newUser(Users user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserActive(true);

        user.addRoles(roleService.getUserRole());
        userRepository.save(user);

    }

    public void save(Users user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public Users getUser(Long id) {
        return userRepository.getOne(id);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean isEmailPresent(String email) {
        List<Users> list = userRepository.findByEmail(email);

        if (!list.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public Users findByEmail(String email) {
        List<Users> list = userRepository.findByEmail(email);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public int userDiabled(String email) {
        List<Users> list = userRepository.findByEmail(email);
        if (list.isEmpty()) {   //  user  do not  exist
            return 0;
        } else {
            if (list.get(0).isUserActive()) {
                return 1;   // user  exit and active  
            } else {
                return 2;             // user  exist  and not  active
            }
        }
    }

    /**
     * returns all Users with Admin Role
     *
     * @return
     */
    public List<Users> findUser_Admin() {
        Roles adm = roleService.getAdminRole();
        return userRepository.findAdminUser(adm.getName());

    }

    /**
     * returns all Users with User Role
     *
     * @return
     */
    public List<Users> findUser_User() {
        Roles usr = roleService.getUserRole();
        return userRepository.findAdminUser(usr.getName());

    }
}
