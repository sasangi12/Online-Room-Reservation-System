package lk.icbt.ovr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.icbt.ovr.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);

    boolean existsByReservationNumber(String reservationNumber);
}