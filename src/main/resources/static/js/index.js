// document.addEventListener("DOMContentLoaded", function () {
//     const userGreeting = document.getElementById("title");
//     const logoutBtn = document.getElementById("logoutBtn");
//
const username = localStorage.getItem("username");
let isAdmin = localStorage.getItem('admin');// This assumes you store the admin status in localStorage
const userId = Number(localStorage.getItem("userId"));
console.log("User ID from localStorage:", userId);
console.log("User ID from localStorage:", username);
document.addEventListener("DOMContentLoaded", function () {
       getTierLists();  // Call this when the page is loaded to show the tier lists
       getAllTierLists();
});




async function submitTierList() {
       const username = localStorage.getItem('username'); // Retrieve logged-in user
       if (!username) {
              alert("User not logged in!");
              return;
       }

       const tierName = document.getElementById('tierName').value;
       const items = {};
       document.querySelectorAll('.tier-input').forEach(input => {
              items[input.dataset.rank] = input.value;
       });

       const response = await fetch('/tier-lists', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({
                     username: username,
                     tierName: tierName,
                     items: JSON.stringify(items) // Convert to JSON string
              })
       });

       const result = await response.text();
       alert(result);
}

async function getTierLists() {
       const username = localStorage.getItem('username');
       if (!username) {
              alert("User not logged in!");
              return;
       }

       const response = await fetch(`/tier-lists/${username}`);
       const tierLists = await response.json();

       let output = "<h2>Your Tier Lists:</h2>";

       tierLists.forEach(list => {
              output += `<div class="tier-box">`;
              output += `<h3>${list.tierName}</h3><ul class="tier-list">`;

              const items = JSON.parse(list.items);
              if (items) {
                     for (let rank in items) {
                            output += `<li><strong>${rank}:</strong> ${items[rank]}</li>`;
                     }
              } else {
                     output += "<p>No items in this tier list</p>";
              }

              output += "</ul></div>"; // Close tier-box div
       });
       document.getElementById("userTierLists").innerHTML = output;
}

async function getAllTierLists() {
       const userId = localStorage.getItem("userId"); // Get the current user's ID from localStorage
       if (!userId) {
              console.error("User ID not found in localStorage.");
              return;
       }

       try {
              const response = await fetch('/tier-lists/all', {
                     method: 'GET',
                     headers: {
                            'userId': userId // Pass the userId as a header
                     }
              });

              if (response.ok) {
                     const tierLists = await response.json();
                     if (tierLists.length === 0) {
                            console.log("No tier lists found.");
                     }

                     let output = "<h2>All Tier Lists:</h2>";
                     tierLists.forEach(list => {
                            output += `<div class="tier-box">`;
                            output += `<h3>${list.tierName}</h3><ul class="tier-list">`;

                            const items = JSON.parse(list.items);
                            if (items) {
                                   for (let rank in items) {
                                          output += `<li><strong>${rank}:</strong> ${items[rank]}</li>`;
                                   }
                            } else {
                                   output += "<p>No items in this tier list</p>";
                            }

                            output += "</ul></div>"; // Close tier-box div
                     });


                     document.getElementById("allTierLists").innerHTML = output;
              } else {
                     console.error("Failed to fetch tier lists:", response.status, response.statusText);
              }
       } catch (error) {
              console.error("Error fetching tier lists:", error);
       }
}








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