package org.example.introspringboot.services.implementations;

import jakarta.transaction.Transactional;
import org.example.introspringboot.entity.Account;
import org.example.introspringboot.repository.AccountRepository;
import org.example.introspringboot.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void transferMoney(Long fromAccountId, long toAccountId, Double amount) {
        //shorter version by transforming optional into a null if it doesn't exist
        Account accountFrom = accountRepository.findById(fromAccountId).orElse(null);
        Account accountTo = accountRepository.findById(toAccountId).orElse(null);

        //longer version that check if it exists and then extracts it
        //if(accountRepository.findById(toAccountId).isPresent()){
            //accountRepository.findById(toAccountId);
        //}

        //subtract the amount from the FromAccount
        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountRepository.save(accountFrom);

        //throw an exception to show what happens when there is an error
//        if(true){
//            throw new RuntimeException("Error! Conditions are restored.");
//        }

        //add the amount to the toAccount
        accountTo.setBalance(accountTo.getBalance() + amount);
        accountRepository.save(accountTo);
    }
}
