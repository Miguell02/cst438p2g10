document.addEventListener("DOMContentLoaded", function () {
    const username = localStorage.getItem("username");
    const userId = Number(localStorage.getItem("userId"));
    console.log("User ID from localStorage:", userId);
    const logoutBtn = document.getElementById("logoutBtn");

    if (!username || !userId) {
        alert("Please log in first!");
        window.location.href = "login.html";
        return;
    }

    // Logout
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function () {
            localStorage.removeItem("username");
            alert("Bye bye!");
            window.location.href = "login.html";
        });
    }

    // Fetch user profile
    fetch(`/users/profile?username=${username}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById("username").innerText = user.username;
            document.getElementById("email").innerText = user.email;

            // Populate form fields with current data for editing
            document.getElementById("editUsername").value = user.username;
            document.getElementById("editEmail").value = user.email;
        })
        .catch(error => console.error("Error fetching profile:", error));

    // Handle profile update
    const updateForm = document.getElementById("updateForm");
    if (updateForm) {
        updateForm.addEventListener("submit", function (event) {
            event.preventDefault();

            const newUsername = document.getElementById("editUsername").value;
            const newEmail = document.getElementById("editEmail").value;
            const newPassword = document.getElementById("editPassword").value;

            const updatedUser = {
                username: newUsername,
                email: newEmail,
                password: newPassword // Make sure you hash the password on the backend
            };

            // Send the update request to the backend
            fetch(`/users/update/${userId}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedUser)
            })
                .then(response => response.json())
                .then(updatedUser => {
                    if (updatedUser) {
                        alert("Profile updated successfully!");
                        localStorage.setItem("username", updatedUser.username);
                        window.location.href = "profile.html"; // Redirect to another page after update
                    } else {
                        alert("Failed to update profile.");
                    }
                })
                .catch(error => {
                    console.error("Error updating profile:", error);
                    alert("An error occurred while updating your profile.");
                });
        });
    }

    // Handle account deletion
    document.getElementById("deleteAccount").addEventListener("click", function () {
        const password = prompt("Enter your password to confirm account deletion:");

        if (!password) {
            alert("Password is required to delete your account.");
            return;
        }

        if (confirm("Are you sure you want to delete your account? This action cannot be undone!")) {
            console.log("Deleting user with ID:", userId);
            console.log("Password provided:", password);
            fetch(`/users/delete/${userId}?password=${encodeURIComponent(password)}`, {
                method: "DELETE"
            })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    if (message.includes("successfully")) {
                        localStorage.clear();
                        window.location.href = "login.html";  // Redirect back to login after account deletion
                    }
                })
                .catch(error => console.error("Error deleting account:", error));
        }
    });

    let isAdmin = localStorage.getItem('admin'); // This assumes you store the admin status in localStorage
    if (username === 'admin') {
        isAdmin = localStorage.setItem(true);
    }
    // If the user is not an admin, hide the "Admin" link
    if (isAdmin !== 'true') {
        document.getElementById('adminLink').style.display = 'none';
    }
});
