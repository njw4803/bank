package njw.project.bank.domain.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import njw.project.bank.domain.account.Account;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // 스프링이 User 객체 생성할 때 빈생성자로 new 하기 때문에!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // account.getUser().아무필드호출() == Lazy 발동
    private Account withdrawAccount;

    @ManyToOne(fetch = FetchType.LAZY) // account.getUser().아무필드호출() == Lazy 발동
    private Account depositAccount;

    @Column(nullable = false)
    private Long amount;

    private Long withdrawAccountGBalance; // 1111 계좌 -> 1000월 -> 500원 -> 200원 계좌 잔액 history
    private Long depositAccountGBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // WITHDRAW, DEPOSIT, TRANSFER, ALL

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountGBalance, Long depositAccountGBalance, TransactionEnum gubun, String sender, String receiver, String tel, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountGBalance = withdrawAccountGBalance;
        this.depositAccountGBalance = depositAccountGBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
