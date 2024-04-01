package co.com.devsu.bank.account.model.repository;

import co.com.devsu.bank.account.model.entities.TransactionAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionAccountEntityRepository extends JpaRepository<TransactionAccountEntity, UUID> {

    List<TransactionAccountEntity> findAllByAccountId(UUID accountId);

    List<TransactionAccountEntity> findAllByAccountIdAndTransactionDateBetween(UUID accountId, LocalDateTime from, LocalDateTime to);

    void deleteAllByAccountId(UUID uuid);
}
