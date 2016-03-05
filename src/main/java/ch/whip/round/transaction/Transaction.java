package ch.whip.round.transaction;

import ch.whip.round.account.GroupAccount;
import ch.whip.round.member.Member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private GroupAccount groupAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currencyCode = "CHF";

    @ManyToOne
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupAccount getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(GroupAccount groupAccount) {
        this.groupAccount = groupAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
