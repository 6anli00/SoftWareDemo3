function fetchContacts() {
    fetch('http://localhost:8080')
        .then(response => response.text())
        .then(data => {
            const contacts = JSON.parse(data);
            const contactsList = document.getElementById('contacts');
            contactsList.innerHTML = '';

            contacts.forEach(contact => {
                const contactElement = document.createElement('li');
                contactElement.textContent = `${contact.name} - ${contact.address} - ${contact.phone}`;
                contactsList.appendChild(contactElement);
            });
        })
        .catch(error => console.error)}