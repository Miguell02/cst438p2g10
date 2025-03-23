document.getElementById("viewUsersBtn").addEventListener("click", async () => {
    try {
        const res = await fetch('/admin/users?username=admin&password=ott3r');
        const users = await res.json();
        window.userList = users;

        const tbody = document.getElementById("usersTableBody");
        tbody.innerHTML = "";

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
            tbody.appendChild(row);
        });
    } catch (err) {
        console.error("Failed to load users:", err);
        alert("Error loading users.");
    }
});

window.deleteUser = async function (username) {
    if (!confirm(`Are you sure you want to delete ${username}?`)) return;

    const user = window.userList.find(u => u.username === username);
    if (!user) {
        alert("User not found.");
        return;
    }

    try {
        const res = await fetch(`/admin/users/${user.id}?username=admin&password=ott3r&confirm=true`, {
            method: 'DELETE'
        });

        const msg = await res.text();
        alert(msg);
        document.getElementById("viewUsersBtn").click(); // Refresh table
    } catch (error) {
        console.error("Delete error:", error);
        alert("Failed to delete user.");
    }
};

window.updateUser = async function (username) {
    const newEmail = prompt(`Enter new email for ${username}:`);
    const newPassword = prompt(`Enter new password for ${username}:`);

    if (!newEmail || !newPassword) {
        alert("Update cancelled.");
        return;
    }

    const user = window.userList.find(u => u.username === username);
    if (!user) {
        alert("User not found.");
        return;
    }

    try {
        const res = await fetch(`/admin/users?username=admin&password=ott3r&updateUsername=${username}&newEmail=${encodeURIComponent(newEmail)}&newPassword=${encodeURIComponent(newPassword)}`, {
            method: 'PATCH'
        });

        const msg = await res.text();
        alert(msg);
        document.getElementById("viewUsersBtn").click(); // Refresh table
    } catch (error) {
        console.error("Update error:", error);
        alert("Failed to update user.");
    }
};

document.getElementById("createUserForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("newUsername").value.trim();
    const email = document.getElementById("newEmail").value.trim();
    const password = document.getElementById("newPassword").value;

    if (!username || !email || !password) {
        alert("All fields are required.");
        return;
    }

    const userData = {
        username: username,
        password: password,
        email: email
    };

    try {
        const res = await fetch(`/admin/users?username=admin&password=ott3r`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        const msg = await res.text();
        alert(msg);

        // Clear form
        document.getElementById("createUserForm").reset();

        // Refresh the user table
        document.getElementById("viewUsersBtn").click();

    } catch (error) {
        console.error("Create user error:", error);
        alert("Failed to create user.");
    }
});
