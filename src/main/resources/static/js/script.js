document.addEventListener("DOMContentLoaded", function () {
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
                // First, try to log in
                const loginResponse = await fetch("http://localhost:8080/users/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(loginData),
                });

                if (loginResponse.ok) {
                    // If login is successful, fetch the userId by username
                    const userIdResponse = await fetch(`http://localhost:8080/users/userId?username=${username}`, {
                        method: "GET",
                    });

                    if (userIdResponse.ok) {
                        const userIdData = await userIdResponse.json();
                        const userId = userIdData; // Extract the userId
                        console.log(userId);

                        // Store userId in localStorage
                        localStorage.setItem("username", username);
                        localStorage.setItem("userId", userId);

                        alert("Login successful!");
                        window.location.href = "index.html";
                    } else {
                        const errorData = await userIdResponse.json();
                        alert("User ID fetch failed: " + errorData.message);
                    }
                } else {
                    const loginErrorData = await loginResponse.json();
                    alert("Login failed: " + loginErrorData.message);
                }
            } catch (error) {
                console.error("Error:", error);
                alert("An error occurred during login");
            }
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const signupForm = document.getElementById("signupForm");

    if (signupForm) {
        signupForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const name = document.getElementById("name").value.trim();
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {
                alert("Passwords do not match.");
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

                const result = await response.text();
                alert(result);

                if (response.ok) {
                    window.location.href = "login.html"; // Redirect to login after signup
                }

            } catch (err) {
                console.error("Signup error:", err);
                alert("An error occurred while creating your account.");
            }
        });
    }
});
