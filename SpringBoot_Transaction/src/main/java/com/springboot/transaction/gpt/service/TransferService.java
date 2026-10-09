package com.springboot.transaction.gpt.service;

import com.springboot.transaction.gpt.entity.Account;
import com.springboot.transaction.gpt.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(
            String fromAccount,
            String toAccount,
            BigDecimal amount) {

        // 1. find sender
        Account sender = accountRepository
                .findByAccountNumber(fromAccount)
                .orElseThrow(() -> new RuntimeException("Sender Account not Found"));

        // 2. find receiver
        Account receiver = accountRepository
                .findByAccountNumber(toAccount)
                .orElseThrow(() -> new RuntimeException("Receiver Account not Found"));

        // 3. check balance
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        // 4. debit from sender
        sender.setBalance(sender.getBalance().subtract(amount));

        // 5. credit to receiver
        receiver.setBalance(receiver.getBalance().add(amount));

        // 6. save both accounts
        //accountRepository.save(sender);
        accountRepository.save(receiver);

        // Simulate an error to test transaction rollback
        // Uncomment the following line to test rollback
        //throw new RuntimeException("Something went wrong during the transfer. Transaction will be rolled back.");


    }
}
