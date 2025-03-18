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
                alert("Passwords don't match, brah!");
                return;
            }

            const userData = {
                username: name,
                email: email,
                password: password,
            };

            try {
                const response = await fetch("http://localhost:8080/users/newuser", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userData),
                });

                if (response.ok) {
                    alert("Signup successful! Going to login, brah...");
                    window.location.href = "login.html";
                } else {
                    const errorData = await response.json();
                    alert("Signup has failed: " + (errorData.message || "Unknown error"));
                }
            } catch (error) {
                console.error("Error:", error);
                alert("An error occurred you asshole");
            }
        });
    }

    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", async function (event) {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            const loginData = {
                username: username,
                password: password,
            };

            try {
                const response = await fetch("http://localhost:8080/users/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(loginData),
                });

                const textResponse = await response.text();
                if (response.ok) {
                    alert(textResponse);
                    localStorage.setItem("username", username);
                    window.location.href = "dashboard.html";
                } else {
                    alert("Login DIDN'T WORK: " + textResponse);
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Something went wrong, FUCK");
            }
        });
    }
});