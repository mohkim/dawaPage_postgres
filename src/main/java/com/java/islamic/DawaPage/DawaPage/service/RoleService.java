/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.service;

import com.java.islamic.DawaPage.DawaPage.entity.Roles;
import com.java.islamic.DawaPage.DawaPage.repository.CommentRepository;
import com.java.islamic.DawaPage.DawaPage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KEMAL
 */
@Repository
public class RoleService {
    @Autowired
    public  RoleRepository roleRepository;
    
    
    
    public void  newRole(Roles  role){
           
        roleRepository.save(role);
      
    }
    public  void  deleteRole(Long  id){
        
        roleRepository.deleteById(id);
    }
    
    public  Roles  getRole(Long  id){
      return  roleRepository.getOne(id);
    }
    
    public  Roles  getAdminRole(){
        Roles  role= roleRepository.findByName("ADMIN");
        if(role==null){
            roleRepository.save(new  Roles("ADMIN"));
           return  roleRepository.findByName("ADMIN");
        } else
            return  roleRepository.findByName("ADMIN");
    }
    
     public  Roles  getUserRole(){
         Roles  role= roleRepository.findByName("USER");
        if(role==null){
            roleRepository.save(new  Roles("USER"));
           return  roleRepository.findByName("USER");
        } else
            return  roleRepository.findByName("USER");
    }
}
