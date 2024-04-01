package co.com.devsu.bank.account.service.impl;


import co.com.devsu.bank.account.constants.IdentificationType;
import co.com.devsu.bank.account.constants.MessageResponseConstant;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.AccountEntity;
import co.com.devsu.bank.account.model.repository.IAccountEntityRepository;
import co.com.devsu.bank.account.model.repository.ITransactionAccountEntityRepository;
import co.com.devsu.bank.account.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    private final IAccountEntityRepository accountEntityRepository;

    public AccountServiceImpl(IAccountEntityRepository accountEntityRepository) {
        this.accountEntityRepository = accountEntityRepository;
    }

    @Override
    public List<AccountEntity> report(String identificationNumber, IdentificationType identificationType) throws BusinessException {

        UUID clientId = accountEntityRepository.findClientIdByIdentificationNumberAndIdentificationType(identificationNumber, identificationType)
                .orElseThrow(() -> new BusinessException(MessageResponseConstant.CLIENT_NOT_EXIST));

        return accountEntityRepository.findAllByClientId(clientId);
    }
}
