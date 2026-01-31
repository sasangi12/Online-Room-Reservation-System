package lk.icbt.ovr.repository;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import lk.icbt.ovr.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);

    boolean existsByReservationNumber(String reservationNumber);
    
    List<lk.icbt.ovr.entity.Reservation> findByCheckInDateBetween(LocalDate from, LocalDate to);
}