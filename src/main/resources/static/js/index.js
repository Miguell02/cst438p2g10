// document.addEventListener("DOMContentLoaded", function () {
//     const userGreeting = document.getElementById("title");
//     const logoutBtn = document.getElementById("logoutBtn");
//
const username = localStorage.getItem("username");
let isAdmin = localStorage.getItem('admin');// This assumes you store the admin status in localStorage

if (username === 'admin'){
       isAdmin = localStorage.setItem(true);
}


// If the user is not an admin, hide the "Admin" link
if (isAdmin !== 'true') {
       document.getElementById('adminLink').style.display = 'none';
}
//
//     if (username) {
//         userGreeting.textContent = `Hello, ${username}!`;
//     } else {
//         window.location.href = "login.html";
//     }
// });