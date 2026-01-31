document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("reservationForm");
  const msg = document.getElementById("msg");
  const fillDemo = document.getElementById("fillDemo");

  fillDemo.addEventListener("click", () => {
    document.getElementById("reservationNumber").value = "RES-4001";
    document.getElementById("roomType").value = "DOUBLE";
    document.getElementById("guestName").value = "Kamal Perera";
    document.getElementById("address").value = "Galle, Sri Lanka";
    document.getElementById("contactNumber").value = "+94771234567";
    document.getElementById("checkInDate").value = "2026-02-03";
    document.getElementById("checkOutDate").value = "2026-02-06";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.innerHTML = "";

    const payload = {
      reservationNumber: document.getElementById("reservationNumber").value.trim(),
      guestName: document.getElementById("guestName").value.trim(),
      address: document.getElementById("address").value.trim(),
      contactNumber: document.getElementById("contactNumber").value.trim(),
      roomType: document.getElementById("roomType").value.trim(),
      checkInDate: document.getElementById("checkInDate").value,
      checkOutDate: document.getElementById("checkOutDate").value,
    };

    try {
      const created = await apiRequest("/api/reservations", {
        method: "POST",
        headers: getJsonHeaders(),
        body: JSON.stringify(payload),
      });

      msg.innerHTML = `<div class="alert success">✅ Reservation saved: <b>${created.reservationNumber}</b></div>`;
      form.reset();

    } catch (err) {
      // show validation details if available
      let extra = "";
      if (err.data && err.data.validationErrors) {
        const entries = Object.entries(err.data.validationErrors)
          .map(([k,v]) => `<li><b>${k}</b>: ${v}</li>`).join("");
        extra = `<ul style="margin:10px 0 0 18px;color:var(--muted)">${entries}</ul>`;
      }
      msg.innerHTML = `<div class="alert error">❌ ${err.message}${extra}</div>`;
    }
  });
});