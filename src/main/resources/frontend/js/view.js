document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("viewForm");
  const msg = document.getElementById("msg");
  const result = document.getElementById("result");
  const demoFind = document.getElementById("demoFind");

  demoFind.addEventListener("click", () => {
    document.getElementById("searchNumber").value = "RES-4001";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.innerHTML = "";
    result.innerHTML = "";

    const resNo = document.getElementById("searchNumber").value.trim();

    try {
      const data = await apiRequest(`/api/reservations/${encodeURIComponent(resNo)}`);

      result.innerHTML = `
        <table class="table">
          <tr><th>Reservation Number</th><td>${data.reservationNumber}</td></tr>
          <tr><th>Guest Name</th><td>${data.guestName}</td></tr>
          <tr><th>Address</th><td>${data.address}</td></tr>
          <tr><th>Contact</th><td>${data.contactNumber}</td></tr>
          <tr><th>Room Type</th><td>${data.roomType}</td></tr>
          <tr><th>Check-in</th><td>${data.checkInDate}</td></tr>
          <tr><th>Check-out</th><td>${data.checkOutDate}</td></tr>
        </table>
      `;

      msg.innerHTML = `<div class="alert success">✅ Reservation found.</div>`;
    } catch (err) {
      result.innerHTML = `<div class="notice">No reservation loaded yet.</div>`;
      msg.innerHTML = `<div class="alert error">❌ ${err.message}</div>`;
    }
  });
});