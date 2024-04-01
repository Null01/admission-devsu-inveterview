package co.com.devsu.bank.account.model.repository;

import co.com.devsu.bank.account.constants.IdentificationType;
import co.com.devsu.bank.account.model.entities.AccountEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public interface IAccountEntityRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByAccountNumber(BigInteger accountNumber);

    List<AccountEntity> findAllByClientId(UUID clientId);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "SELECT cl.id FROM clients.client cl LEFT JOIN clients.person p ON (cl.person_id = p.id) WHERE identification_number = :id AND identification_type = :type")
    Optional<UUID> findClientIdByIdentificationNumberAndIdentificationType(@Param("id") String identificationNumber, @Param("type") IdentificationType identificationType);

}
