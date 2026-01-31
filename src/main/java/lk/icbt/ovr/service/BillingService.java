package lk.icbt.ovr.service;

import lk.icbt.ovr.dto.BillResponseDTO;

public interface BillingService {
    BillResponseDTO generateBill(String reservationNumber);
}