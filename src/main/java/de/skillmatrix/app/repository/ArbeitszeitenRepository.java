package de.skillmatrix.app.repository;

import de.skillmatrix.app.domain.Arbeitszeiten;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Arbeitszeiten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArbeitszeitenRepository extends JpaRepository<Arbeitszeiten, Long> {
}
