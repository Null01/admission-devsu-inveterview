package co.com.devsu.bank.account.service;

import co.com.devsu.bank.account.constants.IdentificationType;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.AccountEntity;

import java.util.List;


public interface IAccountService {

    List<AccountEntity> report(String identificationNumber, IdentificationType identificationType) throws BusinessException;
}
