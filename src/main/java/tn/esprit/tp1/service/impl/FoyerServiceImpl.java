package tn.esprit.tp1.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entity.Bloc;
import tn.esprit.tp1.entity.Foyer;
import tn.esprit.tp1.entity.Universite;
import tn.esprit.tp1.repository.FoyerRepository;
import tn.esprit.tp1.repository.UniversiteRepository;
import tn.esprit.tp1.service.FoyerService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FoyerServiceImpl implements FoyerService {

    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    // ------------------ CRUD ------------------
    @Override
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public Foyer updateFoyer(Long id, Foyer foyer) {
        Foyer existing = foyerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foyer introuvable avec ID : " + id));

        if (foyer.getNomFoyer() != null) existing.setNomFoyer(foyer.getNomFoyer());
        if (foyer.getCapaciteFoyer() != null) existing.setCapaciteFoyer(foyer.getCapaciteFoyer());
        if (foyer.getBlocs() != null) existing.setBlocs(foyer.getBlocs());

        return foyerRepository.save(existing);
    }

    @Override
    public void deleteFoyer(Long id) {
        if (!foyerRepository.existsById(id))
            throw new RuntimeException("Foyer introuvable avec ID : " + id);
        foyerRepository.deleteById(id);
    }

    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer getFoyerById(Long id) {
        return foyerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foyer introuvable avec ID : " + id));
    }

    @Override
    public Foyer saveFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    // ------------------ Ajout foyer + blocs + affectation université ------------------
    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, Long idUniversite) {
        // 1️⃣ Récupérer l'université
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université introuvable avec ID : " + idUniversite));

        // 2️⃣ Affecter le foyer à l'université
        foyer.setUniversite(universite);
        universite.setFoyer(foyer); // navigation inverse

        // 3️⃣ Affecter chaque bloc au foyer
        if (foyer.getBlocs() != null) {
            for (Bloc bloc : foyer.getBlocs()) {
                bloc.setFoyer(foyer);
            }
        }

        // 4️⃣ Sauvegarder le foyer (et tous les blocs automatiquement grâce à cascade)
        return foyerRepository.save(foyer);
    }

}
