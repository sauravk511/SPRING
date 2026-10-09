package com.springboot.transaction.controller;

import com.springboot.transaction.dto.TransferRequest;
import com.springboot.transaction.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestBody TransferRequest request) {

        System.out.println("========== TRANSFER REQUEST ==========");
        System.out.println("fromAccount = " + request.getFromAccount());
        System.out.println("toAccount   = " + request.getToAccount());
        System.out.println("amount      = " + request.getAmount());
        System.out.println("======================================");

        transferService.transfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );

        return ResponseEntity.ok(
                "Transfer successful"
        );
    }
}