document.addEventListener("DOMContentLoaded", function () {
    const viewUsersBtn = document.getElementById("viewUsersBtn");
    const usersTableBody = document.getElementById("usersTableBody");
    const createUserForm = document.getElementById("createUserForm");

    const API_URL = "http://localhost:8080/users/all";

    // Fetch and display all users
    viewUsersBtn.addEventListener("click", async function () {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) throw new Error("Failed to fetch users");

            const users = await response.json();
            usersTableBody.innerHTML = ""; // Clear existing data

            users.forEach(user => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>
                        <button onclick="updateUser('${user.username}')">Edit</button>
                        <button onclick="deleteUser('${user.username}')">Delete</button>
                    </td>
                `;
                usersTableBody.appendChild(row);
            });
        } catch (error) {
            console.error("Error fetching users:", error);
            alert("Could not retrieve users.");
        }
    });

    // Create a new user
    const API_URL_CREATE_USER = "http://localhost:8080/users";
    createUserForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const newUsername = document.getElementById("newUsername").value.trim();
        const newEmail = document.getElementById("newEmail").value.trim();
        const newPassword = document.getElementById("newPassword").value;

        if (!newUsername || !newEmail || !newPassword) {
            alert("Please fill in all fields.");
            return;
        }

        try {
            const response = await fetch(API_URL_CREATE_USER + "/newuser", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username: newUsername, email: newEmail, password: newPassword }),
            });

            if (response.ok) {
                alert("User created successfully!");
                createUserForm.reset();
                viewUsersBtn.click(); // Refresh user list
            } else {
                const errorData = await response.json();
                alert("Error: " + (errorData.message || "Failed to create user"));
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred. Please try again.");
        }
    });

    // Update a user
    window.updateUser = async function (username) {
        const newEmail = prompt(`Enter new email for ${username}:`);
        const newPassword = prompt(`Enter new password for ${username}:`);

        if (!newEmail || !newPassword) {
            alert("Update cancelled.");
            return;
        }

        //IMPLEMENT DELETE USER LOGIC NEXT
    };

    // Delete a user
    window.deleteUser = async function (username) {
        if (!confirm(`Are you sure you want to delete ${username}?`)) return;

        //IMPLEMENT DELETE USER LOGIC NEXT
    };
});
