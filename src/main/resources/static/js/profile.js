document.addEventListener("DOMContentLoaded", function () {
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
            document.getElementById("username").innerText = user.username;
            document.getElementById("email").innerText = user.email;
            document.getElementById("password").innerText = user.password;
        })
        .catch(error => console.error("Error fetching profile:", error));

    document.getElementById("deleteAccount").addEventListener("click", function () {
        const password = prompt("Enter your password to confirm account deletion:");

        if (!password) {
            alert("Password is required to delete your account.");
            return;
        }

        if (confirm("Are you sure you want to delete your account? This action cannot be undone!")) {
            fetch(`/users/delete/${userId}?password=${encodeURIComponent(password)}`, {
                method: "DELETE"
            })
                .then(response => response.text())
                .then(message => {
                    alert(message);
                    if (message.includes("successfully")) {
                        localStorage.clear();
                        window.location.href = "login.html";  //sends user back to login after deleting account
                    }
                })
                .catch(error => console.error("Error deleting account:", error));
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
