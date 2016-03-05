package ch.whip.round.transaction;

import ch.whip.round.account.GroupAccount;
import ch.whip.round.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransaction(BigDecimal amount, GroupAccount account, Member member) {
        Transaction transaction = new Transaction();
        transaction.setGroupAccount(account);
        transaction.setAmount(amount);
        transaction.setMember(member);
        transactionRepository.save(transaction);
    }
}
