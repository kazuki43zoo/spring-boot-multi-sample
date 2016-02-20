package com.github.kazuki43zoo.sample.domain.repository;

import com.github.kazuki43zoo.sample.domain.model.Account;

public interface AccountRepository {

    Account findOne(String accountId);

    Account findOneByUsername(String username);

}
