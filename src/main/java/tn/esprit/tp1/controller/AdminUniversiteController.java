package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Bloc;
import tn.esprit.tp1.entity.Foyer;
import tn.esprit.tp1.entity.Universite;
import tn.esprit.tp1.service.BlocService;
import tn.esprit.tp1.service.FoyerService;
import tn.esprit.tp1.service.UniversiteService;

import java.util.List;

@Tag(name = "Administration Universités", description = "Endpoints avancés pour affectation/désaffectation des foyers")
@RestController
@RequestMapping("/admin")
public class AdminUniversiteController {

    private final UniversiteService universiteService;
    private final FoyerService foyerService;
    private final BlocService blocService;

    // ✅ Injection via constructeur
    public AdminUniversiteController(UniversiteService universiteService,
                                     FoyerService foyerService,
                                     BlocService blocService) {
        this.universiteService = universiteService;
        this.foyerService = foyerService;
        this.blocService = blocService;
    }

    // ------------------ Affecter un foyer à une université ------------------
    @Operation(summary = "Affecter un foyer à une université")
    @PostMapping("/affecterFoyer")
    public Universite affecterFoyer(
            @RequestParam Long idFoyer,
            @RequestParam String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
    }

    // ------------------ Désaffecter le foyer d'une université ------------------
    @Operation(summary = "Désaffecter le foyer d'une université")
    @PostMapping("/desaffecterFoyer")
    public Universite desaffecterFoyer(@RequestParam Long idUniversite) {
        return universiteService.desaffecterFoyer(idUniversite);
    }

    // ------------------ Affecter plusieurs chambres à un bloc ------------------
    @Operation(summary = "Affecter plusieurs chambres à un bloc")
    @PostMapping("/affecterChambres")
    public Bloc affecterChambresABloc(
            @RequestParam Long idBloc,
            @RequestParam List<Long> idsChambres) {
        return blocService.affecterChambresABloc(idsChambres, idBloc);
    }
    // ------------------ Ajouter un foyer avec ses blocs et l’affecter à une université ------------------
    @Operation(summary = "Ajouter un foyer avec ses blocs et l’affecter à une université")
    @PostMapping("/ajouterFoyerEtAffecter")
    public Foyer ajouterFoyerEtAffecter(
            @RequestParam Long idUniversite,
            @RequestBody Foyer foyer) {
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
    }




}
