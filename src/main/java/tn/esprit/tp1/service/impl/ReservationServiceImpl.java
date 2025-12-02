package tn.esprit.tp1.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tp1.entity.Bloc;
import tn.esprit.tp1.entity.Chambre;
import tn.esprit.tp1.entity.Etudiant;
import tn.esprit.tp1.entity.Reservation;
import tn.esprit.tp1.repository.BlocRepository;
import tn.esprit.tp1.repository.ChambreRepository;
import tn.esprit.tp1.repository.EtudiantRepository;
import tn.esprit.tp1.repository.ReservationRepository;
import tn.esprit.tp1.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final BlocRepository blocRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;
    private final ReservationRepository reservationRepository;

    // ✅ Injection des services/repos nécessaires
    public ReservationServiceImpl(BlocRepository blocRepository,
                                  ChambreRepository chambreRepository,
                                  EtudiantRepository etudiantRepository,
                                  ReservationRepository reservationRepository) {
        this.blocRepository = blocRepository;
        this.chambreRepository = chambreRepository;
        this.etudiantRepository = etudiantRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation ajouterReservation(Long idBloc, Long idEtudiant) {

        // 1️⃣ Récupérer le bloc
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec ID : " + idBloc));

        // 2️⃣ Choisir une chambre disponible
        Chambre chambreDisponible = bloc.getChambres().stream()
                .filter(ch -> ch.getReservations().size() < ch.getCapacite())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucune chambre disponible dans ce bloc"));

        // 3️⃣ Récupérer l'étudiant
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec ID : " + idEtudiant));

        // 4️⃣ Créer une réservation SIMPLE correspondant à ton entity
        Reservation reservation = Reservation.builder()
                .nomReserver(chambreDisponible.getNomChambre() + "-" + bloc.getNomBloc())
                .dateDebut(LocalDate.now())
                .dateFin(LocalDate.now().plusMonths(9))
                .build();

        // 5️⃣ Ajouter l'étudiant à la liste ManyToMany
        reservation.getEtudiants().add(etudiant);

        // 6️⃣ Ajouter la réservation à la chambre (si ta relation le prévoit)
        chambreDisponible.getReservations().add(reservation);

        // 7️⃣ Sauvegarder
        return reservationRepository.save(reservation);
    }



}
