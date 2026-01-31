package lk.icbt.ovr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.icbt.ovr.dto.ReservationReportItemDTO;
import lk.icbt.ovr.dto.RevenueReportDTO;
import lk.icbt.ovr.service.ReportsService;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    // GET /api/reports/reservations?from=2026-02-01&to=2026-02-10
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationReportItemDTO>> reservationsByDateRange(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        return ResponseEntity.ok(reportsService.getReservationsByDateRange(fromDate, toDate));
    }

    // GET /api/reports/revenue?from=2026-02-01&to=2026-02-10
    @GetMapping("/revenue")
    public ResponseEntity<RevenueReportDTO> revenueByDateRange(
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        return ResponseEntity.ok(reportsService.getRevenueByDateRange(fromDate, toDate));
    }
}