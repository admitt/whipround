package ch.whip.round.transaction;

import ch.whip.round.account.GroupAccount;
import ch.whip.round.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private GroupAccount groupAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currencyCode = "CHF";

    @Column(nullable = true)
    private String geocode;

    @Column(nullable = true)
    private String details;

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

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) { this.geocode = geocode; }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) { this.details = details; }
}
