package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Bloc;
import tn.esprit.tp1.service.BlocService;

import java.util.List;

@Tag(
        name = "Gestion des Blocs",
        description = "Permet d’ajouter, modifier, supprimer et consulter les blocs du foyer."
)
@RestController
@RequestMapping("/blocs")
public class BlocController {

    private final BlocService blocService;

    // ✅ Injection par constructeur
    public BlocController(BlocService blocService) {
        this.blocService = blocService;
    }

    @Operation(
            summary = "Ajouter un bloc",
            description = "Crée un nouveau bloc dans la base de données."
    )
    @PostMapping
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    @Operation(
            summary = "Modifier un bloc",
            description = "Met à jour les informations d’un bloc existant."
    )
    @PutMapping("/{id}")
    public Bloc updateBloc(@PathVariable Long id, @RequestBody Bloc bloc) {
        return blocService.updateBloc(id, bloc);
    }

    @Operation(
            summary = "Supprimer un bloc",
            description = "Supprime un bloc de la base à partir de son identifiant."
    )
    @DeleteMapping("/{id}")
    public void deleteBloc(@PathVariable Long id) {
        blocService.deleteBloc(id);
    }

    @Operation(
            summary = "Lister tous les blocs",
            description = "Retourne la liste complète des blocs enregistrés dans le système."
    )
    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBlocs();
    }

    @Operation(
            summary = "Récupérer un bloc par ID",
            description = "Retourne les informations d’un bloc à partir de son identifiant."
    )
    @GetMapping("/{id}")
    public Bloc getBlocById(@PathVariable Long id) {
        return blocService.getBlocById(id);
    }
}
