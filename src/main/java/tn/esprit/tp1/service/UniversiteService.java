package tn.esprit.tp1.service;

import tn.esprit.tp1.entity.Universite;
import java.util.List;



public interface UniversiteService {

    Universite addUniversite(Universite universite);

    Universite updateUniversite(Long id, Universite universite);

    void deleteUniversite(Long id);

    List<Universite> getAllUniversites();

    Universite getUniversiteById(Long id);


    Universite getUniversiteByNom(String nomUniversite);

    Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite);

    Universite desaffecterFoyer(Long idUniversite);
}
