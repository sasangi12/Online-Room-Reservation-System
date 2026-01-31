package lk.icbt.ovr.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.icbt.ovr.dto.BillResponseDTO;
import lk.icbt.ovr.entity.Reservation;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.exception.ResourceNotFoundException;
import lk.icbt.ovr.repository.ReservationRepository;

@Service
@Transactional(readOnly = true)
public class BillingServiceImpl implements BillingService {

    private final ReservationRepository reservationRepository;

    public BillingServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public BillResponseDTO generateBill(String reservationNumber) {

        Reservation r = reservationRepository
                .findByReservationNumber(reservationNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found for number: " + reservationNumber));

        long nights = ChronoUnit.DAYS.between(r.getCheckInDate(), r.getCheckOutDate());

        if (nights <= 0) {
            throw new BadRequestException("Invalid stay duration. Check-in and check-out dates are incorrect.");
        }

        BigDecimal rate = getRatePerNight(r.getRoomType());
        BigDecimal total = rate.multiply(BigDecimal.valueOf(nights));

        BillResponseDTO dto = new BillResponseDTO();
        dto.setReservationNumber(r.getReservationNumber());
        dto.setGuestName(r.getGuestName());
        dto.setRoomType(r.getRoomType());
        dto.setCheckInDate(r.getCheckInDate());
        dto.setCheckOutDate(r.getCheckOutDate());
        dto.setNights(nights);
        dto.setRatePerNight(rate);
        dto.setTotalAmount(total);

        return dto;
    }

    // Rate table (simple and fine for assignment)
    private BigDecimal getRatePerNight(String roomType) {

        if (roomType == null) {
            throw new BadRequestException("Room type not found in reservation.");
        }

        String type = roomType.trim().toUpperCase();

        switch (type) {
            case "SINGLE":
                return new BigDecimal("8000.00");
            case "DOUBLE":
                return new BigDecimal("12000.00");
            case "SUITE":
                return new BigDecimal("20000.00");
            default:
                throw new BadRequestException("Invalid room type: " + roomType);
        }
    }
}