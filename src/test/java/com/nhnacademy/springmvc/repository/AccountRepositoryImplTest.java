package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Account;
import com.nhnacademy.springmvc.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryImplTest {

    private AccountRepository accountRepository;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepositoryImpl();
        testAccount = new Account("test", "testPw", "testName", Role.Client);
        accountRepository.addAccount(
                testAccount.getId(),
                testAccount.getPassword(),
                testAccount.getName(),
                testAccount.getRole());
    }

    @Test
    void existAccount_isTrue() {
        assertThat(accountRepository.exists("test")).isTrue();
    }

    @Test
    void notExistAccount_isFalse() {
        assertThat(accountRepository.exists("noExistId")).isFalse();
    }

    @Test
    void addAccountAndGetAccountTest() {
        Account account = accountRepository.getAccount("test");

        assertThat(account).isNotNull();
        assertThat(account).isInstanceOf(Account.class);
        assertThat(account.getId()).isEqualTo(testAccount.getId());
        assertThat(account.getPassword()).isEqualTo(testAccount.getPassword());
        assertThat(account.getName()).isEqualTo(testAccount.getName());
        assertThat(account.getRole()).isEqualTo(testAccount.getRole());

    }

    @Test
    void notExistAccount_getAccount_isNull() {
        assertThat(accountRepository.getAccount("noExistId")).isNull();
    }
    
    







}