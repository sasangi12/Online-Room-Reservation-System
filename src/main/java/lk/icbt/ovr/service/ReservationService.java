package lk.icbt.ovr.service;

import lk.icbt.ovr.dto.ReservationCreateRequestDTO;
import lk.icbt.ovr.dto.ReservationResponseDTO;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationCreateRequestDTO request);
    ReservationResponseDTO getByReservationNumber(String reservationNumber);
}