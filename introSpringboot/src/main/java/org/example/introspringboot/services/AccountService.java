package org.example.introspringboot.services;

public interface AccountService {
    void transferMoney(Long fromAccountId, long toAccountId, Double amount);
}
