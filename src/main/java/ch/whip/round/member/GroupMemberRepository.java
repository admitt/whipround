package ch.whip.round.member;

import ch.whip.round.account.GroupAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    List<GroupMember> findByAccount(GroupAccount account);

    @Query("SELECT DISTINCT gm.account FROM GroupMember gm WHERE gm.member = ?1")
    List<GroupAccount> findGroups(Member member);
}
