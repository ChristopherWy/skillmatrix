package de.skillmatrix.app.repository;

import de.skillmatrix.app.domain.Mitarbeiterskills;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mitarbeiterskills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MitarbeiterskillsRepository extends JpaRepository<Mitarbeiterskills, Long> {
}
