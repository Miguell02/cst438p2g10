document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");
    if (signupForm) {
        signupForm.addEventListener("submit", async function (event) {
            event.preventDefault();

            const name = document.getElementById("name").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {
                alert("Passwords dont match shithead");
                return;
            }

            const userData = {
                username: name,
                email: email,
                password: password
            };

            try {
                const response = await fetch("http://localhost:8080/users/newuser", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userData),
                });

                if (response.ok) {
                    alert("Signup successful! Going to login brah...");
                    window.location.href = "login.html";
                } else {
                    const errorData = await response.json();
                    alert("Signup has fucking failed: " + (errorData.message || "Unknown error"));
                }
            } catch (error) {
                console.error("Error:", error);
                alert("An error occurred. shitass.");
            }
        });
    }

    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async function (event) {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;
            const credentials = { username, password };

            try {
                const response = await fetch(`http://localhost:8080/users/login?username=${username}&password=${password}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                });

                if (response.ok) {
                    const user = await response.json(); // Get user object from response
                    localStorage.setItem("username", user.username);
                    localStorage.setItem("userId", user.id); // Store userId in localStorage

                    if (user.isAdmin) {
                        localStorage.setItem("admin", "true");
                    } else {
                        localStorage.setItem("admin", "false");
                    }

                    alert("Login successful!");
                    window.location.href = "index.html";
                } else {
                    const errorMessage = await response.text();
                    alert("Login DIDNT WORK!!!: " + errorMessage);
                }
            } catch (error) {
                console.error("Error:", error);
                alert("something happened man what the fuck");
            }
        });
    }
});
