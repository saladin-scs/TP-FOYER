package tn.esprit.tp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tp1.entity.Foyer;

import java.util.Optional;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {
    Optional<Object> findByNomFoyer(String nomFoyer);
}
