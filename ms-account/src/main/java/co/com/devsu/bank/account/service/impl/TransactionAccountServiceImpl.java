package co.com.devsu.bank.account.service.impl;

import co.com.devsu.bank.account.constants.MessageResponseConstant;
import co.com.devsu.bank.account.controllers.dto.TransactionAccountRequest;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.AccountEntity;
import co.com.devsu.bank.account.model.entities.TransactionAccountEntity;
import co.com.devsu.bank.account.model.repository.IAccountEntityRepository;
import co.com.devsu.bank.account.model.repository.ITransactionAccountEntityRepository;
import co.com.devsu.bank.account.service.ITransactionAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TransactionAccountServiceImpl implements ITransactionAccountService {

    private final ITransactionAccountEntityRepository transactionAccountEntityRepository;
    private final IAccountEntityRepository accountEntityRepository;

    public TransactionAccountServiceImpl(ITransactionAccountEntityRepository transactionAccountEntityRepository,
                                         IAccountEntityRepository accountEntityRepository) {
        this.transactionAccountEntityRepository = transactionAccountEntityRepository;
        this.accountEntityRepository = accountEntityRepository;
    }

    @Override
    public TransactionAccountEntity register(TransactionAccountRequest request) throws BusinessException {

        AccountEntity account = accountEntityRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new BusinessException(MessageResponseConstant.ACCOUNT_NOT_EXIST));

        BigDecimal balanceTmp = BigDecimal.ZERO;
        switch (request.getTransactionType()) {
            case RETIRO:
                balanceTmp = account.getBalance().subtract(request.getAmount().abs());
                if (balanceTmp.compareTo(BigDecimal.ZERO) < 0)
                    throw new BusinessException(MessageResponseConstant.ACCOUNT_INSUFFICIENT_BALANCE);
                break;

            case DEPOSITO:
                balanceTmp = account.getBalance().add(request.getAmount().abs());
                break;
        }

        try {

            account.setBalance(balanceTmp);
            accountEntityRepository.save(account);

            return transactionAccountEntityRepository.save(
                    TransactionAccountEntity.builder()
                            .accountId(account.getId())
                            .balance(account.getBalance())
                            .transactionType(request.getTransactionType())
                            .amount(request.getAmount())
                            .build()
            );

        } catch (Exception e) {
            log.error("NOT WAS POSSIBLE CREATE A CLIENT.", e);
            throw new BusinessException("Not was possible create the transaction.");
        }
    }

    @Override
    public List<TransactionAccountEntity> getAllByAccountId(UUID accountId, LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null)
            return transactionAccountEntityRepository.findAllByAccountId(accountId);
        return transactionAccountEntityRepository.findAllByAccountIdAndTransactionDateBetween(accountId, from, to);
    }

    @Override
    public Optional<TransactionAccountEntity> getById(UUID id) {
        return this.transactionAccountEntityRepository.findById(id);
    }

    @Override
    public List<TransactionAccountEntity> getAll() {
        return this.transactionAccountEntityRepository.findAll();
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<TransactionAccountEntity> m = transactionAccountEntityRepository.findById(id);
        if (m.isPresent())
            this.transactionAccountEntityRepository.deleteById(id);
        return m.isPresent();
    }

    @Override
    public Boolean deleteByAccountId(UUID id) {
        transactionAccountEntityRepository.deleteAllByAccountId(id);
        return Boolean.TRUE;
    }

}
