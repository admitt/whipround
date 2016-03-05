package ch.whip.round.transaction;

import ch.whip.round.account.GroupAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

interface TransactionRepository extends CrudRepository<Transaction, Long>{
    List<Transaction> findByGroupAccount(GroupAccount groupAccount);

    @Query("SELECT sum(amount) FROM Transaction where groupAccount=?1")
    BigDecimal accountBalance(GroupAccount groupAccount);
}
