/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.islamic.DawaPage.DawaPage.repository;

import com.java.islamic.DawaPage.DawaPage.entity.ConfirmationToken;
import com.java.islamic.DawaPage.DawaPage.entity.Users;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author FREE_MIND
 */
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByUser(Users  user);
}
