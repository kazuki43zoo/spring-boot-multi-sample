package com.github.kazuki43zoo.sample.domain.service;

import com.github.kazuki43zoo.sample.domain.model.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private final Account account;

    public CustomUser(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(account.getAuthorities()));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}
