package lk.icbt.ovr.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.icbt.ovr.dto.ReservationCreateRequestDTO;
import lk.icbt.ovr.dto.ReservationResponseDTO;
import lk.icbt.ovr.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // POST /api/reservations
    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationCreateRequestDTO request) {
        ReservationResponseDTO created = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/reservations/{reservationNumber}
    @GetMapping("/{reservationNumber}")
    public ResponseEntity<ReservationResponseDTO> getByNumber(@PathVariable String reservationNumber) {
        ReservationResponseDTO reservation = reservationService.getByReservationNumber(reservationNumber);
        return ResponseEntity.ok(reservation);
    }
}