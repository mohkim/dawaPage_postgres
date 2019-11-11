/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.Roles;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author KEMAL
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    public List<Users> findByEmail(String email);

  @Query( "select  u from Users u   join fetch u.roles r  where r.name=?1" )  //where r=?1
    public List<Users> findAdminUser(String name);//
    
   

}
