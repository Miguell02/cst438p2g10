document.addEventListener("DOMContentLoaded", function () {
    const userGreeting = document.getElementById("userGreeting");
    const logoutBtn = document.getElementById("logoutBtn");

    const username = localStorage.getItem("username");

    if (username) {
        userGreeting.textContent = `Hello, ${username}! Welcome back.`;
    } else {
        window.location.href = "login.html";
    }

    // Logoutt
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            localStorage.removeItem("username");
            alert("Bye bye asshole");
            window.location.href = "login.html";
        });
    }
});