package lk.icbt.ovr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.icbt.ovr.dto.BillResponseDTO;
import lk.icbt.ovr.service.BillingService;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    // GET /api/billing/{reservationNumber}
    @GetMapping("/{reservationNumber}")
    public ResponseEntity<BillResponseDTO> getBill(@PathVariable String reservationNumber) {
        BillResponseDTO bill = billingService.generateBill(reservationNumber);
        return ResponseEntity.ok(bill);
    }
}