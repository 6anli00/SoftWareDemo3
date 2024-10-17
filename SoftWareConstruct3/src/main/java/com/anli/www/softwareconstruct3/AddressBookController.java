package com.anli.www.softwareconstruct3;



import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class AddressBookController {
    @Autowired
    private ContactRepository contactRepository;

    // 获取所有联系人
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // 添加新联系人
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // 修改联系人
    @PutMapping("/{id}")
    public Contact updateContact(@RequestBody Contact contact, @PathVariable Long id) {
        contact.setId(id);
        return contactRepository.save(contact);
    }

    // 删除联系人
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

    // 获取单个联系人
    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactRepository.findById(id).orElse(null);
    }
}
