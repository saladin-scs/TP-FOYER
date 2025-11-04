package tn.esprit.tp1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entity.Reservation;
import tn.esprit.tp1.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservation Management", description = "CRUD operations for reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Add a new reservation", description = "Creates a new reservation in the system")
    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @Operation(summary = "Update an existing reservation", description = "Updates the details of a reservation")
    @PutMapping
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @Operation(summary = "Delete a reservation by ID", description = "Deletes the reservation with the given ID")
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @Operation(summary = "Get all reservations", description = "Returns the list of all reservations")
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Get a reservation by ID", description = "Returns a specific reservation by its ID")
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }
}
