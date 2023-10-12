package njw.project.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositry extends JpaRepository<Account, Long> {
}
