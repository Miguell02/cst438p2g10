document.addEventListener("DOMContentLoaded", () => {
    const updateBtn = document.getElementById("updateProfileBtn"); // Ensure you have this button in your HTML
    const usernameInput = document.getElementById("usernameInput");
    const passwordInput = document.getElementById("passwordInput");

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
            const response = await fetch(`https://yourapi.com/users/${userId}`, {
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
});
