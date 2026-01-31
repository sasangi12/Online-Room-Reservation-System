package lk.icbt.ovr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueReportDTO {

    private LocalDate from;
    private LocalDate to;
    private long totalReservations;
    private BigDecimal totalRevenue;

    public RevenueReportDTO() {}

    public LocalDate getFrom() { return from; }
    public void setFrom(LocalDate from) { this.from = from; }

    public LocalDate getTo() { return to; }
    public void setTo(LocalDate to) { this.to = to; }

    public long getTotalReservations() { return totalReservations; }
    public void setTotalReservations(long totalReservations) { this.totalReservations = totalReservations; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}