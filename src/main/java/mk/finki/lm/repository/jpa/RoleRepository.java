package mk.finki.lm.repository.jpa;

import mk.finki.lm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);

}
