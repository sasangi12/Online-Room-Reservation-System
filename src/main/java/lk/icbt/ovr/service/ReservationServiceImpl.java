package lk.icbt.ovr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.icbt.ovr.dto.ReservationCreateRequestDTO;
import lk.icbt.ovr.dto.ReservationResponseDTO;
import lk.icbt.ovr.entity.Reservation;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.exception.ResourceNotFoundException;
import lk.icbt.ovr.repository.ReservationRepository;

import java.time.LocalDate;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationResponseDTO createReservation(ReservationCreateRequestDTO request) {

        String resNo = request.getReservationNumber().trim();

        if (reservationRepository.existsByReservationNumber(resNo)) {
            throw new BadRequestException("Reservation number already exists: " + resNo);
        }

        if (request.getCheckOutDate().isBefore(request.getCheckInDate()) ||
            request.getCheckOutDate().isEqual(request.getCheckInDate())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(resNo);
        reservation.setGuestName(request.getGuestName().trim());
        reservation.setAddress(request.getAddress().trim());
        reservation.setContactNumber(request.getContactNumber().trim());
        reservation.setRoomType(request.getRoomType().trim().toUpperCase());
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());

        Reservation saved = reservationRepository.save(reservation);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO getByReservationNumber(String reservationNumber) {

        Reservation reservation = reservationRepository
                .findByReservationNumber(reservationNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found for number: " + reservationNumber
                ));

        return mapToResponse(reservation);
    }

    private ReservationResponseDTO mapToResponse(Reservation r) {

        ReservationResponseDTO dto = new ReservationResponseDTO();

        dto.setReservationNumber(r.getReservationNumber());
        dto.setGuestName(r.getGuestName());
        dto.setAddress(r.getAddress());
        dto.setContactNumber(r.getContactNumber());
        dto.setRoomType(r.getRoomType());
        dto.setCheckInDate(r.getCheckInDate());
        dto.setCheckOutDate(r.getCheckOutDate());

        return dto;
    }
}