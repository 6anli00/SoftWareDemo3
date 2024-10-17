// 获取DOM元素
const contactList = document.getElementById('contacts');

// 获取表单元素
const nameInput = document.getElementById('name');
const addressInput = document.getElementById('address');
const phoneInput = document.getElementById('phone');

// 获取所有联系人
function getAllContacts() {
    fetch('/api/contacts')
        .then(response => response.json())
        .then(contacts => {
            contactList.innerHTML = '';
            contacts.forEach(contact => {
                const contactElement = document.createElement('li');
                contactElement.textContent = `${contact.name} - ${contact.address} - ${contact.phone}`;
                contactList.appendChild(contactElement);
            });
        })
        .catch(error => console.error('Error fetching contacts:', error));
}

// 添加联系人
function addContact() {
    const contact = {
        name: nameInput.value,
        address: addressInput.value,
        phone: phoneInput.value
    };

    fetch('/api/contacts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(contact),
    })
    .then(response => response.json())
    .then(contact => {
        const contactElement = document.createElement('li');
        contactElement.textContent = `${contact.name} - ${contact.address} - ${contact.phone}`;
        contactList.appendChild(contactElement);
        // 清空表单
        nameInput.value = '';
        addressInput.value = '';
        phoneInput.value = '';
    })
    .catch(error => console.error('Error adding contact:', error));
}

// 初始化页面
getAllContacts();