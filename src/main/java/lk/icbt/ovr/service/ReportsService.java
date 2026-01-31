package lk.icbt.ovr.service;

import java.time.LocalDate;
import java.util.List;

import lk.icbt.ovr.dto.ReservationReportItemDTO;
import lk.icbt.ovr.dto.RevenueReportDTO;

public interface ReportsService {

    List<ReservationReportItemDTO> getReservationsByDateRange(LocalDate from, LocalDate to);

    RevenueReportDTO getRevenueByDateRange(LocalDate from, LocalDate to);
}