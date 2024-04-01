package co.com.devsu.bank.account.service;

import co.com.devsu.bank.account.controllers.dto.TransactionAccountRequest;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.TransactionAccountEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionAccountService {

    @Transactional
    TransactionAccountEntity register(TransactionAccountRequest request) throws BusinessException;

    List<TransactionAccountEntity> getAllByAccountId(UUID accountId, final LocalDateTime from, final LocalDateTime to);

    Optional<TransactionAccountEntity> getById(UUID id);

    List<TransactionAccountEntity> getAll();

    Boolean delete(UUID id);

    Boolean deleteByAccountId(UUID id);
}
