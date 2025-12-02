package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Universite;
import tn.esprit.tp1.service.UniversiteService;

import java.util.List;

@Tag(
        name = "Gestion des Universités",
        description = "CRUD complet pour la gestion des universités"
)
@RestController
@RequestMapping("/universites")
public class UniversiteController {

    private final UniversiteService universiteService;

    // ✅ Injection via constructeur
    public UniversiteController(UniversiteService universiteService) {
        this.universiteService = universiteService;
    }

    @Operation(summary = "Ajouter une université", description = "Crée une nouvelle université dans la base de données")
    @PostMapping
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }

    @Operation(summary = "Modifier une université", description = "Met à jour les informations d'une université existante")
    @PutMapping("/{id}")
    public Universite updateUniversite(@PathVariable Long id, @RequestBody Universite universite) {
        return universiteService.updateUniversite(id, universite);
    }

    @Operation(summary = "Supprimer une université", description = "Supprime une université à partir de son identifiant")
    @DeleteMapping("/{id}")
    public void deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
    }

    @Operation(summary = "Lister toutes les universités", description = "Retourne la liste complète des universités")
    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversites();
    }

    @Operation(summary = "Récupérer une université par ID", description = "Retourne les informations d'une université à partir de son identifiant")
    @GetMapping("/{id}")
    public Universite getUniversiteById(@PathVariable Long id) {
        return universiteService.getUniversiteById(id);
    }








}
