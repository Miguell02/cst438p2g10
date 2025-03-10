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
});
