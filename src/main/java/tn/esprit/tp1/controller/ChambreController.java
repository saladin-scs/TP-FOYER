package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Chambre;
import tn.esprit.tp1.service.ChambreService;

import java.util.List;

@Tag(name = "Gestion des Chambres", description = "CRUD complet pour la gestion des chambres et leur lien avec les blocs")
@RestController
@RequestMapping("/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    //  Injection via constructeur
    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    @Operation(summary = "Ajouter une chambre", description = "Crée une nouvelle chambre dans la base de données")
    @PostMapping
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreService.addChambre(chambre);
    }

    @Operation(summary = "Modifier une chambre", description = "Met à jour les informations d'une chambre existante")
    @PutMapping
    public Chambre updateChambre(@RequestBody Chambre chambre) {
        return chambreService.updateChambre(chambre);
    }

    @Operation(summary = "Supprimer une chambre", description = "Supprime une chambre à partir de son identifiant")
    @DeleteMapping("/{id}")
    public void deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
    }

    @Operation(summary = "Lister toutes les chambres", description = "Retourne la liste complète des chambres")
    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.allChambres();
    }

    @Operation(summary = "Lister les chambres d'un bloc", description = "Retourne toutes les chambres appartenant à un bloc donné")
    @GetMapping("/bloc/{idBloc}")
    public List<Chambre> getChambresByBloc(@PathVariable Long idBloc) {
        return chambreService.findByBlocId(idBloc);
    }
}
