document.addEventListener("DOMContentLoaded", () => {
  // If already logged in, go to reservation page
  if (isLoggedIn()) window.location.href = "reservation.html";

  const form = document.getElementById("loginForm");
  const msg = document.getElementById("msg");
  const fillBtn = document.getElementById("fillBtn");

  fillBtn.addEventListener("click", () => {
    document.getElementById("username").value = "admin";
    document.getElementById("password").value = "admin123";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.innerHTML = "";

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    try {
      const res = await apiRequest("/api/auth/login", {
        method: "POST",
        headers: getJsonHeaders(),
        body: JSON.stringify({ username, password }),
      });

      setLoggedIn(res.username || username);
      msg.innerHTML = `<div class="alert success">✅ ${res.message || "Login successful"}</div>`;
      setTimeout(() => (window.location.href = "reservation.html"), 600);

    } catch (err) {
      msg.innerHTML = `<div class="alert error">❌ ${err.message}</div>`;
    }
  });
});