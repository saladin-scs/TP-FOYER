package tn.esprit.tp1.service;

import tn.esprit.tp1.entity.Reservation;
import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation reservation);

    Reservation updateReservation(Reservation reservation);

    void deleteReservation(Long id);

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long id);


    Reservation ajouterReservation(Long idBloc, Long idEtudiant);

}
