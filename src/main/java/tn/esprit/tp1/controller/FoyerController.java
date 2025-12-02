package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Foyer;
import tn.esprit.tp1.entity.Universite;
import tn.esprit.tp1.service.FoyerService;

import java.util.List;

@Tag(
        name = "Gestion des Foyers",
        description = "Endpoints pour la création, la mise à jour, la suppression et la consultation des foyers."
)
@RestController
@RequestMapping("/foyers")
public class FoyerController {

    private final FoyerService foyerService;

    // ✅ Injection via constructeur
    public FoyerController(FoyerService foyerService) {
        this.foyerService = foyerService;
    }

    @Operation(
            summary = "Ajouter un foyer",
            description = "Crée un nouveau foyer et l’enregistre dans la base de données."
    )
    @PostMapping
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @Operation(
            summary = "Mettre à jour un foyer",
            description = "Modifie les informations d’un foyer existant à partir de son ID."
    )
    @PutMapping("/{id}")
    public Foyer updateFoyer(@PathVariable Long id, @RequestBody Foyer foyer) {
        return foyerService.updateFoyer(id, foyer);
    }

    @Operation(
            summary = "Supprimer un foyer",
            description = "Supprime un foyer en utilisant son identifiant."
    )
    @DeleteMapping("/{id}")
    public void deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
    }

    @Operation(
            summary = "Lister tous les foyers",
            description = "Retourne la liste complète des foyers enregistrés."
    )
    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @Operation(
            summary = "Récupérer un foyer par ID",
            description = "Retourne les détails d’un foyer spécifique à partir de son identifiant."
    )
    @GetMapping("/{id}")
    public Foyer getFoyerById(@PathVariable Long id) {
        return foyerService.getFoyerById(id);
    }




}
