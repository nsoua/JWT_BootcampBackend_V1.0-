package com.sip.ams.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sip.ams.entities.Role;
import com.sip.ams.models.ERole;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
	// Role findByRole(String role);
	//Optional<Role> findByRole(Set<Role> roles);

	Optional<Role> findByName(ERole name);
}
