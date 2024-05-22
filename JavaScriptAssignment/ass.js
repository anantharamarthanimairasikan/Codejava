// const form = document.getElementById('use-case-form');
// const tableBody = document.getElementById('userdetails');
// const userArray = [];

// form.addEventListener('submit', function(event) {
//   event.preventDefault();

//   const name = document.getElementById('name').value;
//   const email = document.getElementById('email').value;
//   const contact = document.getElementById('contact').value;
//   const accountType = document.getElementById('accountType').value;

//   const user = {
//     name: name,
//     email: email,
//     contact: contact,
//     accountType: accountType
//   };

//   userArray.push(user);

//   const row = document.createElement('tr');
//   const nameCell = document.createElement('td');
//   const emailCell = document.createElement('td');
//   const contactCell = document.createElement('td');
//   const accountTypeCell = document.createElement('td');

//   nameCell.textContent = name;
//   emailCell.textContent = email;
//   contactCell.textContent = contact;
//   accountTypeCell.textContent = accountType;

//   row.appendChild(nameCell);
//   row.appendChild(emailCell);
//   row.appendChild(contactCell);
//   row.appendChild(accountTypeCell);

//   tableBody.appendChild(row);
// });

// document.getElementById('use-case-form').addEventListener('submit', function(event) {
//     event.preventDefault();
 
//     var name = document.getElementById('name').value;
//     var email = document.getElementById('email').value;
//     var contact = document.getElementById('contact').value;
//     var accountType = document.getElementById('account-type').value;
 
//     // Create a new table row
//     var newRow = document.createElement('tr');
//     newRow.innerHTML = '<td>' + name + '</td><td>' + email + '</td><td>' +contact + '</td><td>'+accountType+'</td>';

//     // Append the new row to the table body
//     document.querySelector('#userdetails tbody').appendChild(newRow);
 
//     // Clear the form fields
//     document.getElementById('name').value = '';
//     document.getElementById('email').value = '';
//     document.getElementById('contact').value = '';
//     document.getElementById('account-type').value = '';
//   });

// Get the form and table elements
const form = document.getElementById('use-case-form');
const tableBody = document.querySelector('#userdetails tbody');
const customerHistory = [];

// Add an event listener to the form to handle submission
// form.addEventListener('submit', function(event) {
//   event.preventDefault();

//   // Get the form field values
//   const name = document.getElementById('name').value;
//   const email = document.getElementById('email').value;
//   const contact = document.getElementById('contact').value;
//   const accountType = document.getElementById('account-type').value;

//   // Create a new table row
//   const newRow = document.createElement('tr');
//   newRow.innerHTML = `<td>${name}</td><td>${email}</td><td>${contact}</td><td>${accountType}</td>`;

//   // Append the new row to the table body
//   tableBody.appendChild(newRow);

//   // Add the customer details to the customer history array
//   customerHistory.push({ name, email, contact, accountType });

//   // Clear the form fields
//   document.getElementById('name').value = '';
//   document.getElementById('email').value = '';
//   document.getElementById('contact').value = '';
//   document.getElementById('account-type').value = 'None';
// });

document.addEventListener("DOMContentLoaded", function() {
  const form = document.getElementById("use-case-form");
  const tableBody = document.querySelector("#userdetails tbody");

  function isValidMobileNumber(mobileNumber) {
    const regex = /^[7-9]\d{9}$/;
    return regex.test(mobileNumber);
  }

  function validateName(name) {
    var nameRegex = /^[A-Za-z]+$/;
    return nameRegex.test(name);
    }
    // Function to validate email
  function validateEmail(email) {
    var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    return emailRegex.test(email);
  }

  form.addEventListener("submit", function(event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const contact = document.getElementById("contact").value;
    const accountType = document.getElementById("account-type").value;

    if (name && email && contact && accountType !== "None")  {
      if (validateName(name) && validateEmail(email) && isValidMobileNumber(contact)){
                const newRow = document.createElement("tr");
            newRow.innerHTML = `
              <td>${name}</td>
              <td>${email}</td>
              <td>${contact}</td>
              <td>${accountType}</td>
            `;
            tableBody.appendChild(newRow);

            form.reset();
      }else{
        alert("Invalid input on fields on name, email, contact no");
      }
    } else {
      alert("Please fill out all fields, select an account type");
    }
  });
});
