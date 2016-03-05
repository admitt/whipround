package ch.whip.round.account;

import ch.whip.round.member.GroupMembers;
import ch.whip.round.member.Member;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class GroupAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member manager;

    @OneToOne(cascade = CascadeType.PERSIST)
    private GroupMembers members = new GroupMembers();

    @Column(nullable = false)
    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getManager() {
        return manager;
    }

    public void setManager(Member manager) {
        this.manager = manager;
    }

    public GroupMembers getMembers() {
        return members;
    }

    public void setMembers(GroupMembers members) {
        this.members = members;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
