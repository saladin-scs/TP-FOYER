package tn.esprit.tp1.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entity.Foyer;
import tn.esprit.tp1.entity.Universite;
import tn.esprit.tp1.repository.UniversiteRepository;
import tn.esprit.tp1.service.FoyerService;
import tn.esprit.tp1.service.UniversiteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversiteServiceImpl implements UniversiteService {

    private final UniversiteRepository universiteRepository;
    private final FoyerService foyerService; // injecté pour gérer les foyers

    // ------------------ CRUD ------------------
    @Override
    public Universite addUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public Universite updateUniversite(Long id, Universite universite) {
        Universite existing = universiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Université introuvable avec ID : " + id));

        if (universite.getNomUniversite() != null)
            existing.setNomUniversite(universite.getNomUniversite());

        if (universite.getAdresse() != null)
            existing.setAdresse(universite.getAdresse());

        return universiteRepository.save(existing);
    }

    @Override
    public void deleteUniversite(Long id) {
        if (!universiteRepository.existsById(id)) {
            throw new RuntimeException("Université introuvable avec ID : " + id);
        }
        universiteRepository.deleteById(id);
    }

    @Override
    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite getUniversiteById(Long id) {
        return universiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Université introuvable avec ID : " + id));
    }

    @Override
    public Universite getUniversiteByNom(String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new RuntimeException("Université introuvable avec nom : " + nomUniversite);
        }
        return universite;
    }

    // ------------------ AFFECTATIONS ------------------

    @Override
    public Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite) {
        Foyer foyer = foyerService.getFoyerById(idFoyer);
        Universite universite = getUniversiteByNom(nomUniversite);

        // Affectation bidirectionnelle
        foyer.setUniversite(universite);   // côté propriétaire
        universite.setFoyer(foyer);        // côté inverse

        // Persistance obligatoire côté foyer
        foyerService.saveFoyer(foyer);

        return universite;
    }

    @Override
    public Universite desaffecterFoyer(Long idUniversite) {
        Universite universite = getUniversiteById(idUniversite);
        Foyer foyer = universite.getFoyer();

        if (foyer != null) {
            foyer.setUniversite(null);
            universite.setFoyer(null);
            foyerService.saveFoyer(foyer);
        }

        return universite;
    }
}
