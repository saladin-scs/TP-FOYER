package tn.esprit.tp1.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entity.Bloc;
import tn.esprit.tp1.entity.Chambre;
import tn.esprit.tp1.repository.BlocRepository;
import tn.esprit.tp1.repository.ChambreRepository;
import tn.esprit.tp1.service.BlocService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlocServiceImpl implements BlocService {

    private final BlocRepository blocRepository;
    private final ChambreRepository chambreRepository; // ✅ injection manquante

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc updateBloc(Long id, Bloc bloc) {
        Bloc existingBloc = blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec ID : " + id));

        if (bloc.getNomBloc() != null) {
            existingBloc.setNomBloc(bloc.getNomBloc());
        }

        if (bloc.getFoyer() != null) {
            existingBloc.setFoyer(bloc.getFoyer());
        }

        return blocRepository.save(existingBloc);
    }

    @Override
    public void deleteBloc(Long id) {
        if (!blocRepository.existsById(id)) {
            throw new RuntimeException("Bloc introuvable avec ID : " + id);
        }
        blocRepository.deleteById(id);
    }

    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec ID : " + id));
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> idsChambres, Long idBloc) {
        // 1️⃣ Récupérer le bloc
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec ID : " + idBloc));

        // 2️⃣ Récupérer les chambres
        List<Chambre> chambres = chambreRepository.findAllById(idsChambres);

        if (chambres.isEmpty()) {
            throw new RuntimeException("Aucune chambre trouvée pour les IDs fournis");
        }

        // 3️⃣ Affecter chaque chambre au bloc
        chambres.forEach(chambre -> chambre.setBloc(bloc));

        // 4️⃣ Sauvegarder les chambres
        chambreRepository.saveAll(chambres);

        // 5️⃣ Facultatif : mettre à jour la liste de chambres dans le bloc
        bloc.setChambres(chambres);

        return bloc;
    }
}
