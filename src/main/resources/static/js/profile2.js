document.addEventListener("DOMContentLoaded", function () {
    const updateBtn = document.getElementById("updateProfileBtn");
    const usernameInput = document.getElementById("usernameInput");
    const passwordInput = document.getElementById("passwordInput");
    const username = localStorage.getItem("username");
    const userId = localStorage.getItem("userId");
    const logoutBtn = document.getElementById("logoutBtn");


    if (!username || !userId) {
        alert("Please log in first!");
        window.location.href = "login.html";
        return;
    }
    // Logoutt
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            localStorage.removeItem("username");
            alert("Bye bye asshole");
            window.location.href = "login.html";
        });
    }

    fetch(`/users/profile?username=${username}`)
        .then(response => response.json())
        .then(user => {
            document.get
            document.getElementById("name").innerText = user.username;
            document.getElementById("email").innerText = user.email;
            document.getElementById("password").innerText = user.password;
            document.getElementById("username").innerText = user.username;
        })
        .catch(error => console.error("Error fetching profile:", error));






    updateBtn.addEventListener("click", async () => {
        const newUsername = usernameInput.value.trim();
        const newPassword = passwordInput.value.trim();

        if (!newUsername && !newPassword) {
            alert("Please enter a new username or password.");
            return;
        }

        const userId = localStorage.getItem("userId"); // Assume user ID is stored in local storage
        const token = localStorage.getItem("token"); // Assume authentication token is stored

        if (!userId || !token) {
            alert("User not authenticated. Please log in again.");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/users/${userId}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}` // Assuming JWT token-based authentication
                },
                body: JSON.stringify({
                    username: newUsername || undefined, // Only send if provided
                    password: newPassword || undefined
                })
            });

            if (!response.ok) {
                throw new Error("Failed to update profile. Please try again.");
            }

            const data = await response.json();
            alert("Profile updated successfully!");
            document.getElementById("usernameHeader").textContent = data.username; // Update UI dynamically
            document.getElementById("usernameDetail").textContent = data.username;

            localStorage.setItem("username", data.username); // Update local storage if needed
        } catch (error) {
            console.error("Error updating profile:", error);
            alert(error.message);
        }
    });

    let isAdmin = localStorage.getItem('admin'); // This assumes you store the admin status in localStorage

    if (username === 'admin'){
        isAdmin = localStorage.setItem(true);
    }
    // If the user is not an admin, hide the "Admin" link
    if (isAdmin !== 'true') {
        document.getElementById('adminLink').style.display = 'none';
    }



});
