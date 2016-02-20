package com.github.kazuki43zoo.sample.domain.service;

import com.github.kazuki43zoo.sample.domain.model.Account;
import com.github.kazuki43zoo.sample.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = Optional.ofNullable(accountRepository.findOneByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new CustomUser(account);
    }

}