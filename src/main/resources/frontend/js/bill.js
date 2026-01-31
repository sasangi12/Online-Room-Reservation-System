document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("billForm");
  const msg = document.getElementById("msg");
  const billResult = document.getElementById("billResult");
  const demoBill = document.getElementById("demoBill");

  demoBill.addEventListener("click", () => {
    document.getElementById("billNumber").value = "RES-4001";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.innerHTML = "";
    billResult.innerHTML = "";

    const resNo = document.getElementById("billNumber").value.trim();

    try {
      const bill = await apiRequest(`/api/billing/${encodeURIComponent(resNo)}`);

      billResult.innerHTML = `
        <table class="table">
          <tr><th>Reservation Number</th><td>${bill.reservationNumber}</td></tr>
          <tr><th>Guest Name</th><td>${bill.guestName}</td></tr>
          <tr><th>Room Type</th><td>${bill.roomType}</td></tr>
          <tr><th>Check-in</th><td>${bill.checkInDate}</td></tr>
          <tr><th>Check-out</th><td>${bill.checkOutDate}</td></tr>
          <tr><th>Nights</th><td>${bill.nights}</td></tr>
          <tr><th>Rate / Night</th><td>${bill.ratePerNight}</td></tr>
          <tr><th>Total</th><td><b>${bill.totalAmount}</b></td></tr>
        </table>
      `;

      msg.innerHTML = `<div class="alert success">✅ Bill generated successfully.</div>`;
    } catch (err) {
      billResult.innerHTML = `<div class="notice">No bill generated yet.</div>`;
      msg.innerHTML = `<div class="alert error">❌ ${err.message}</div>`;
    }
  });
});