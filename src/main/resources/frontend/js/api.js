// Change this if your backend runs on a different port
const BASE_URL = "http://localhost:8080";

function getJsonHeaders() {
  return { "Content-Type": "application/json" };
}

async function apiRequest(path, options = {}) {
  const url = `${BASE_URL}${path}`;
  const response = await fetch(url, options);

  // Try to parse JSON (both success and error)
  let data = null;
  try {
    data = await response.json();
  } catch (e) {
    // Sometimes backend may return empty body
  }

  if (!response.ok) {
    // Build a clean error message
    const msg =
      (data && data.message) ||
      (data && data.error) ||
      `Request failed with status ${response.status}`;
    const err = new Error(msg);
    err.status = response.status;
    err.data = data;
    throw err;
  }

  return data;
}

// Basic session helpers
function setLoggedIn(username) {
  sessionStorage.setItem("loggedIn", "true");
  sessionStorage.setItem("username", username || "");
}

function isLoggedIn() {
  return sessionStorage.getItem("loggedIn") === "true";
}

function logout() {
  sessionStorage.removeItem("loggedIn");
  sessionStorage.removeItem("username");
  window.location.href = "login.html";
}

function protectPage() {
  if (!isLoggedIn()) {
    window.location.href = "login.html";
  }
}

function setActiveNav(id) {
  const el = document.getElementById(id);
  if (el) el.classList.add("active");
}