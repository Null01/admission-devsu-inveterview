package co.com.devsu.bank.client.service;

import co.com.devsu.bank.client.controllers.dto.ClientRequest;
import co.com.devsu.bank.client.exception.BusinessException;
import co.com.devsu.bank.client.model.entities.ClientEntity;

import javax.transaction.Transactional;

public interface IClientService {

    @Transactional
    ClientEntity create(ClientRequest request) throws BusinessException;

}
