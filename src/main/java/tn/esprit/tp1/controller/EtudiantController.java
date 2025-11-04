package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Etudiant;
import tn.esprit.tp1.service.EtudiantService;

import java.util.List;

@Tag(
        name = "Gestion des Étudiants",
        description = "Permet de créer, modifier, supprimer et consulter les étudiants du foyer."
)
@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    // ✅ Injection par constructeur
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @Operation(
            summary = "Ajouter un étudiant",
            description = "Crée un nouvel étudiant dans la base de données."
    )
    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @Operation(
            summary = "Modifier un étudiant",
            description = "Met à jour les informations d’un étudiant existant."
    )
    @PutMapping("/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiant) {
        return etudiantService.updateEtudiant(id, etudiant);
    }

    @Operation(
            summary = "Supprimer un étudiant",
            description = "Supprime un étudiant à partir de son identifiant."
    )
    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
    }

    @Operation(
            summary = "Lister tous les étudiants",
            description = "Retourne la liste complète des étudiants enregistrés."
    )
    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @Operation(
            summary = "Récupérer un étudiant par ID",
            description = "Retourne les informations d’un étudiant à partir de son identifiant."
    )
    @GetMapping("/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id);
    }
}
