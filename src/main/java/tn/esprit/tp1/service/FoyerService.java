package tn.esprit.tp1.service;

import tn.esprit.tp1.entity.Foyer;
import tn.esprit.tp1.entity.Universite;

import java.util.List;

public interface FoyerService {

    // ------------------ CRUD ------------------
    Foyer addFoyer(Foyer foyer);

    Foyer updateFoyer(Long id, Foyer foyer);

    void deleteFoyer(Long id);

    List<Foyer> getAllFoyers();

    Foyer getFoyerById(Long id);


    Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, Long idUniversite);



    Foyer saveFoyer(Foyer foyer);

    // ------------------ Relations ------------------

}
