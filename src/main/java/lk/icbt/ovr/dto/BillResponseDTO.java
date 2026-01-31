package lk.icbt.ovr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillResponseDTO {

    private String reservationNumber;
    private String guestName;
    private String roomType;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private long nights;
    private BigDecimal ratePerNight;
    private BigDecimal totalAmount;

    public BillResponseDTO() {}

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public long getNights() { return nights; }
    public void setNights(long nights) { this.nights = nights; }

    public BigDecimal getRatePerNight() { return ratePerNight; }
    public void setRatePerNight(BigDecimal ratePerNight) { this.ratePerNight = ratePerNight; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}