package co.com.devsu.bank.client.model.repository;

import co.com.devsu.bank.client.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClientEntityRepository extends JpaRepository<ClientEntity, UUID> {
}
