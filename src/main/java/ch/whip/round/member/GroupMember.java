package ch.whip.round.member;

import ch.whip.round.account.GroupAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class GroupMember implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NotNull
    private Member member;
    @OneToOne
    @NotNull
    private GroupAccount account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public GroupAccount getAccount() {
        return account;
    }

    public void setAccount(GroupAccount account) {
        this.account = account;
    }
}
