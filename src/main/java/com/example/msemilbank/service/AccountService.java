package com.example.msemilbank.service;

import com.example.msemilbank.dao.entity.AccountEntity;
import com.example.msemilbank.dao.entity.UserEntity;
import com.example.msemilbank.enums.Currency;
import com.example.msemilbank.dao.repository.AccountRepository;
import com.example.msemilbank.dao.repository.UserRepository;
import com.example.msemilbank.exception.NotFoundException;
import com.example.msemilbank.mapper.AccountMapper;
import com.example.msemilbank.model.get.AccountGetDto;
import com.example.msemilbank.model.set.AccountSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    private AccountEntity findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found by user id " + accountId));
    }

    public List<AccountGetDto> getAllAccount() {
        log.info("ActionLog.getAllAccount.start");
        List<AccountEntity> accountEntities = accountRepository.findAll();
        List<AccountGetDto> accountGetDtos = accountMapper.mapToDtos(accountEntities);
        log.info("ActionLog.getAllAccount.end");
        return accountGetDtos;
    }

    public void createAccount(Long userId, AccountSetDto accountSetDto) {
        log.info("ActionLog.crateAccount.start");
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found by user id " + userId));
        Currency newCurrency = accountSetDto.getCurrency();

        boolean hasAccountWithCurrency = userEntity.getAccountEntities().stream()
                .anyMatch(account -> account.getCurrency().equals(newCurrency));

        if (hasAccountWithCurrency) {
            log.error("ActionLog.crateAccount.error");
            throw new RuntimeException("User already has account with " + newCurrency);
        } else {
            AccountEntity accountEntity = accountMapper.mapToEntity(accountSetDto);
            accountEntity.setAccountNumber(accountSetDto.getCurrency().toString() + userId);
            accountEntity.setUserEntity(userEntity);
            accountRepository.save(accountEntity);
            log.info("ActionLog.crateAccount.end");
        }
    }

    public void transaction(String fromAccount, String toAccount, Double amount) {
        AccountEntity fromAccountE = accountRepository.findByAccountNumber(fromAccount).orElseThrow(null);
        AccountEntity toAccountE = accountRepository.findByAccountNumber(toAccount).orElseThrow(null);

        if (fromAccountE == null || toAccountE == null) {
            throw new RuntimeException("One or both accounts not found");
        }

        if (fromAccountE.getUserEntity().equals(toAccountE.getUserEntity())) {
            if (fromAccountE.getAmount() >= amount) {
                fromAccountE.setAmount(fromAccountE.getAmount() - amount);
                toAccountE.setAmount(toAccountE.getAmount() + Currency.convert(fromAccountE.getCurrency(), toAccountE.getCurrency(), amount));
            } else {
                throw new RuntimeException(String.format("There are not enough funds, available funds %f, funds that are requested to be withdrawn %f", fromAccountE.getAmount(), amount));
            }
        } else{
            if (fromAccountE.getCurrency().equals(toAccountE.getCurrency())){
                if (fromAccountE.getAmount() >= amount * Currency.sameCurrency) {
                    fromAccountE.setAmount(fromAccountE.getAmount() - amount * Currency.sameCurrency);
                    toAccountE.setAmount(toAccountE.getAmount() + Currency.convert(fromAccountE.getCurrency(), toAccountE.getCurrency(), amount));
                } else {
                    throw new RuntimeException(String.format("There are not enough funds, available funds %f %s, funds that are requested to be withdrawn %f %s", fromAccountE.getAmount(), fromAccountE.getCurrency(), amount * Currency.sameCurrency, fromAccountE.getCurrency()));
                }
            } else {
                if (fromAccountE.getAmount() >= amount * Currency.differentCurrency) {
                    fromAccountE.setAmount(fromAccountE.getAmount() - amount * Currency.differentCurrency);
                    toAccountE.setAmount(toAccountE.getAmount() + Currency.convert(fromAccountE.getCurrency(), toAccountE.getCurrency(), amount));
                } else {
                    throw new RuntimeException(String.format("There are not enough funds, available funds %f %s, funds that are requested to be withdrawn %f %s", fromAccountE.getAmount(), fromAccountE.getCurrency(), amount * Currency.differentCurrency, toAccountE.getCurrency()));
                }
            }
        }

        accountRepository.save(fromAccountE);
        accountRepository.save(toAccountE);
    }
}
