package curium.api.repositories;

import curium.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
