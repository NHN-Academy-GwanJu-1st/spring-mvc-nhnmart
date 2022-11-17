package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Account;
import com.nhnacademy.springmvc.domain.Role;

public interface AccountRepository {

    boolean exists(String id);

    Account getAccount(String id);

    boolean matches(String id, String password);

    Account addAccount(String id, String password, String name, Role role);
}
