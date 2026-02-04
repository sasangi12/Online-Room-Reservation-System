document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("reportForm");
  const msg = document.getElementById("msg");
  const revenueBox = document.getElementById("revenueBox");
  const tableWrap = document.getElementById("tableWrap");

  const demoRange = document.getElementById("demoRange");
  const clearBtn = document.getElementById("clearBtn");

  demoRange.addEventListener("click", () => {
    // Use any dates that match your sample reservations
    document.getElementById("fromDate").value = "2026-02-01";
    document.getElementById("toDate").value = "2026-02-10";
  });

  clearBtn.addEventListener("click", () => {
    msg.innerHTML = "";
    revenueBox.innerHTML = "No revenue report generated yet.";
    tableWrap.innerHTML = "No reservation report generated yet.";
    document.getElementById("fromDate").value = "";
    document.getElementById("toDate").value = "";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.innerHTML = "";
    revenueBox.innerHTML = "";
    tableWrap.innerHTML = "";

    const from = document.getElementById("fromDate").value;
    const to = document.getElementById("toDate").value;

    if (!from || !to) {
      msg.innerHTML = `<div class="alert error">❌ Please select both From and To dates.</div>`;
      return;
    }
    if (to < from) {
      msg.innerHTML = `<div class="alert error">❌ 'To' date must be the same or after 'From' date.</div>`;
      return;
    }

    try {
      // Fetch in parallel
      const [reservations, revenue] = await Promise.all([
        apiRequest(`/api/reports/reservations?from=${from}&to=${to}`),
        apiRequest(`/api/reports/revenue?from=${from}&to=${to}`)
      ]);

      // Revenue summary
      revenueBox.innerHTML = `
        <div class="notice" style="background:rgba(10,12,18,.65)">
          <strong>Date range</strong>: ${revenue.from} → ${revenue.to}<br/>
          <strong>Total reservations</strong>: ${revenue.totalReservations}<br/>
          <strong>Total revenue</strong>: <span style="font-size:20px;color:var(--text);font-weight:800">${revenue.totalRevenue}</span>
        </div>
      `;

      // Reservations table
      if (!reservations || reservations.length === 0) {
        tableWrap.innerHTML = `<div class="notice">No reservations found in this date range.</div>`;
      } else {
        const rows = reservations.map((r) => `
          <tr>
            <td>${r.reservationNumber}</td>
            <td>${r.guestName}</td>
            <td>${r.roomType}</td>
            <td>${r.checkInDate}</td>
            <td>${r.checkOutDate}</td>
          </tr>
        `).join("");

        tableWrap.innerHTML = `
          <table class="table">
            <thead>
              <tr>
                <th>Reservation #</th>
                <th>Guest</th>
                <th>Room Type</th>
                <th>Check-in</th>
                <th>Check-out</th>
              </tr>
            </thead>
            <tbody>
              ${rows}
            </tbody>
          </table>
        `;
      }

      msg.innerHTML = `<div class="alert success">✅ Reports generated successfully.</div>`;
    } catch (err) {
      let extra = "";
      if (err.data && err.data.validationErrors) {
        const entries = Object.entries(err.data.validationErrors)
          .map(([k,v]) => `<li><b>${k}</b>: ${v}</li>`).join("");
        extra = `<ul style="margin:10px 0 0 18px;color:var(--muted)">${entries}</ul>`;
      }
      msg.innerHTML = `<div class="alert error">❌ ${err.message}${extra}</div>`;
      revenueBox.innerHTML = `<div class="notice">No revenue report generated yet.</div>`;
      tableWrap.innerHTML = `<div class="notice">No reservation report generated yet.</div>`;
    }
  });
});