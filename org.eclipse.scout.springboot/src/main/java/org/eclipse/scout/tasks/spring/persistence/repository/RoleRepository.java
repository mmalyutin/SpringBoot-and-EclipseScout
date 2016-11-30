package org.eclipse.scout.tasks.spring.persistence.repository;

import org.eclipse.scout.tasks.spring.persistence.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
}
