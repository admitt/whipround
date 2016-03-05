package ch.whip.round.account;

import ch.whip.round.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class GroupAccountService {
    @Autowired
    private GroupAccountRepository groupAccountRepository;

    void createGroupAccount(Member manager, String reason) {
        GroupAccount groupAccount = new GroupAccount();
        groupAccount.setManager(manager);
        groupAccount.setReason(reason);
        groupAccount.getMembers().addMember(manager);
        groupAccountRepository.save(groupAccount);
    }

    void addMember(Member newMember, GroupAccount account) {
        account.getMembers().addMember(newMember);
        groupAccountRepository.save(account);
    }
}
