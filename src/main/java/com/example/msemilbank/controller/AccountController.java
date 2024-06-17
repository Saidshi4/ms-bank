package com.example.msemilbank.controller;

import com.example.msemilbank.model.set.AccountSetDto;
import com.example.msemilbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/users/{userId}")
    public void crateAccount(@PathVariable Long userId, @RequestBody AccountSetDto accountSetDto){
        accountService.createAccount(userId, accountSetDto);
    }

    @PostMapping("from/{fromAccount}/to/{toAccount}/transaction/{amount}")
    public void transaction(@PathVariable String fromAccount, @PathVariable String toAccount, @PathVariable Double amount){
        accountService.transaction(fromAccount, toAccount, amount);
    }
}
