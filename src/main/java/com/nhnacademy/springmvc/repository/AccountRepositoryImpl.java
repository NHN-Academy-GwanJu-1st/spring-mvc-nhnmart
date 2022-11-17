package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Account;
import com.nhnacademy.springmvc.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    private final Map<String, Account> customerMap = new HashMap<>();

    @Override

    public boolean exists(String id) {
        return customerMap.containsKey(id);
    }

    @Override
    public Account getAccount(String id) {
        return exists(id) ? customerMap.get(id) : null;
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getAccount(id))
                .map(customer -> customer.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public Account addAccount(String id, String password, String name, Role role) {

        Account account = new Account(id, password, name, role);

        customerMap.put(id, account);

        return account;
    }
}
