package lk.icbt.ovr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import lk.icbt.ovr.dto.BillResponseDTO;
import lk.icbt.ovr.entity.Reservation;
import lk.icbt.ovr.exception.ResourceNotFoundException;
import lk.icbt.ovr.repository.ReservationRepository;

public class BillingServiceImplTest {

    private ReservationRepository reservationRepository;
    private BillingServiceImpl billingService;

    @BeforeEach
    void setup() {
        reservationRepository = Mockito.mock(ReservationRepository.class);
        billingService = new BillingServiceImpl(reservationRepository);
    }

    @Test
    void generateBill_shouldReturnCorrectTotal() {
        Reservation r = new Reservation();
        r.setReservationNumber("RES-7777");
        r.setGuestName("Nimal Silva");
        r.setRoomType("SINGLE");
        r.setCheckInDate(LocalDate.of(2026, 2, 2));
        r.setCheckOutDate(LocalDate.of(2026, 2, 5)); // 3 nights

        when(reservationRepository.findByReservationNumber("RES-7777"))
            .thenReturn(Optional.of(r));

        BillResponseDTO bill = billingService.generateBill("RES-7777");

        assertEquals(3, bill.getNights());
        assertEquals(new BigDecimal("8000.00"), bill.getRatePerNight());
        assertEquals(new BigDecimal("24000.00"), bill.getTotalAmount());
    }

    @Test
    void generateBill_shouldThrow_whenReservationNotFound() {
        when(reservationRepository.findByReservationNumber("RES-NOT"))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> billingService.generateBill("RES-NOT"));
    }
}
