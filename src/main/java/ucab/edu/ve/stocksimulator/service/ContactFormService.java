package ucab.edu.ve.stocksimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.ContactFormDTO;
import ucab.edu.ve.stocksimulator.dto.request.ContactFormRequestDTO;
import ucab.edu.ve.stocksimulator.model.ContactForm;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.ContactFormRepo;
import ucab.edu.ve.stocksimulator.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactFormService {
    private final ContactFormRepo contactFormRepo;
    private final UserRepo userRepo;

    @Autowired
    public ContactFormService(ContactFormRepo contactFormRepo, UserRepo userRepo) {
        this.contactFormRepo = contactFormRepo;
        this.userRepo = userRepo;
    }

    public List<ContactFormDTO> getAllForms() {
        return mapContactFormListToContactFormDTOList(contactFormRepo.findAll());
    }

    public List<ContactFormDTO> getFormsByUser(String username) {
        return mapContactFormListToContactFormDTOList(contactFormRepo.findByUser(this.userRepo.findByUsername(username)));
    }

    public void addForm(ContactFormRequestDTO contactFormDTO) {
        ContactForm contactForm = new ContactForm();
        contactForm.setUser(userRepo.findByUsername(contactFormDTO.username));
        contactForm.setTextMessage(contactFormDTO.textMessage);
        contactFormRepo.save(contactForm);
    }

    public void deleteForm(Long id) {
        contactFormRepo.deleteById(id);
    }

    public ContactForm getForm(Long id) {
        return contactFormRepo.findById(id).orElse(null);
    }


    public List<ContactFormDTO> mapContactFormListToContactFormDTOList(List<ContactForm> contactForm){
        List<ContactFormDTO> contactFormDTOList = new ArrayList<>();
        if (contactForm.isEmpty()) {
            return contactFormDTOList;
        }
        for (ContactForm contact : contactForm) {
            contactFormDTOList.add(mapContacttoDTO(contact));
        }
        return contactFormDTOList;
    }

    public ContactFormDTO mapContacttoDTO(ContactForm contact){
        ContactFormDTO contactDTO = new ContactFormDTO();
        contactDTO.id = contact.getId();
        contactDTO.username = contact.getUser().getUsername();
        contactDTO.textMessage = contact.getTextMessage();
        return contactDTO;
    }
}
