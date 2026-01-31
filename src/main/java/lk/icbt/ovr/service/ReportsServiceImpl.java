package lk.icbt.ovr.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.icbt.ovr.dto.ReservationReportItemDTO;
import lk.icbt.ovr.dto.RevenueReportDTO;
import lk.icbt.ovr.entity.Reservation;
import lk.icbt.ovr.exception.BadRequestException;
import lk.icbt.ovr.repository.ReservationRepository;

@Service
@Transactional(readOnly = true)
public class ReportsServiceImpl implements ReportsService {

    private final ReservationRepository reservationRepository;

    public ReportsServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationReportItemDTO> getReservationsByDateRange(LocalDate from, LocalDate to) {

        validateDateRange(from, to);

        List<Reservation> reservations = reservationRepository.findByCheckInDateBetween(from, to);

        List<ReservationReportItemDTO> result = new ArrayList<>();

        for (Reservation r : reservations) {
            ReservationReportItemDTO dto = new ReservationReportItemDTO();
            dto.setReservationNumber(r.getReservationNumber());
            dto.setGuestName(r.getGuestName());
            dto.setRoomType(r.getRoomType());
            dto.setCheckInDate(r.getCheckInDate());
            dto.setCheckOutDate(r.getCheckOutDate());
            result.add(dto);
        }

        return result;
    }

    @Override
    public RevenueReportDTO getRevenueByDateRange(LocalDate from, LocalDate to) {

        validateDateRange(from, to);

        List<Reservation> reservations = reservationRepository.findByCheckInDateBetween(from, to);

        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Reservation r : reservations) {

            long nights = ChronoUnit.DAYS.between(r.getCheckInDate(), r.getCheckOutDate());
            if (nights <= 0) continue;

            BigDecimal rate = getRatePerNight(r.getRoomType());
            BigDecimal bill = rate.multiply(BigDecimal.valueOf(nights));

            totalRevenue = totalRevenue.add(bill);
        }

        RevenueReportDTO dto = new RevenueReportDTO();
        dto.setFrom(from);
        dto.setTo(to);
        dto.setTotalReservations(reservations.size());
        dto.setTotalRevenue(totalRevenue);

        return dto;
    }

    private void validateDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required.");
        }
        if (to.isBefore(from)) {
            throw new BadRequestException("'to' date must be the same or after 'from' date.");
        }
    }

    private BigDecimal getRatePerNight(String roomType) {
        if (roomType == null) return BigDecimal.ZERO;

        String type = roomType.trim().toUpperCase();

        switch (type) {
            case "SINGLE":
                return new BigDecimal("8000.00");
            case "DOUBLE":
                return new BigDecimal("12000.00");
            case "SUITE":
                return new BigDecimal("20000.00");
            default:
                return BigDecimal.ZERO;
        }
    }
}