package lk.icbt.ovr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lk.icbt.ovr.dto.ReservationCreateRequestDTO;
import lk.icbt.ovr.dto.ReservationResponseDTO;
import lk.icbt.ovr.entity.Reservation;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.exception.ResourceNotFoundException;
import lk.icbt.ovr.repository.ReservationRepository;

public class ReservationServiceImplTest {

    private ReservationRepository reservationRepository;
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setup() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationServiceImpl(reservationRepository);
    }

    @Test
    void createReservation_shouldThrow_whenReservationNumberAlreadyExists() {
        ReservationCreateRequestDTO req = new ReservationCreateRequestDTO();
        req.setReservationNumber("RES-9001");
        req.setGuestName("Test User");
        req.setAddress("Test Address");
        req.setContactNumber("+94771234567");
        req.setRoomType("SINGLE");
        req.setCheckInDate(LocalDate.of(2026, 2, 1));
        req.setCheckOutDate(LocalDate.of(2026, 2, 3));

        when(reservationRepository.existsByReservationNumber("RES-9001")).thenReturn(true);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservationService.createReservation(req));

        assertTrue(ex.getMessage().contains("already exists"));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void createReservation_shouldThrow_whenCheckoutBeforeCheckin() {
        ReservationCreateRequestDTO req = new ReservationCreateRequestDTO();
        req.setReservationNumber("RES-9002");
        req.setGuestName("Test User");
        req.setAddress("Test Address");
        req.setContactNumber("+94771234567");
        req.setRoomType("SINGLE");
        req.setCheckInDate(LocalDate.of(2026, 2, 5));
        req.setCheckOutDate(LocalDate.of(2026, 2, 3));

        when(reservationRepository.existsByReservationNumber("RES-9002")).thenReturn(false);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> reservationService.createReservation(req));

        assertTrue(ex.getMessage().toLowerCase().contains("check-out"));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void createReservation_shouldCreate_whenValidRequest() {
        ReservationCreateRequestDTO req = new ReservationCreateRequestDTO();
        req.setReservationNumber("RES-9003");
        req.setGuestName("Kamal Perera");
        req.setAddress("Galle");
        req.setContactNumber("+94771234567");
        req.setRoomType("double");
        req.setCheckInDate(LocalDate.of(2026, 2, 1));
        req.setCheckOutDate(LocalDate.of(2026, 2, 4));

        when(reservationRepository.existsByReservationNumber("RES-9003")).thenReturn(false);

        // repository save returns entity with values
        Reservation saved = new Reservation();
        saved.setReservationNumber("RES-9003");
        saved.setGuestName("Kamal Perera");
        saved.setAddress("Galle");
        saved.setContactNumber("+94771234567");
        saved.setRoomType("DOUBLE");
        saved.setCheckInDate(LocalDate.of(2026, 2, 1));
        saved.setCheckOutDate(LocalDate.of(2026, 2, 4));

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        ReservationResponseDTO resp = reservationService.createReservation(req);

        assertEquals("RES-9003", resp.getReservationNumber());
        assertEquals("DOUBLE", resp.getRoomType());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void getByReservationNumber_shouldThrow_whenNotFound() {
        when(reservationRepository.findByReservationNumber("RES-404"))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reservationService.getByReservationNumber("RES-404"));
    }
}
