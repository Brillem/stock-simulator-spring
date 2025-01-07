package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.edu.ve.stocksimulator.dto.ContactFormDTO;
import ucab.edu.ve.stocksimulator.dto.request.ContactFormRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.service.ContactFormService;
import ucab.edu.ve.stocksimulator.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactFormController {
    private final ContactFormService contactFormService;
    private final UserService userService;

    @Autowired
    public ContactFormController(ContactFormService contactFormService, UserService userService) {
        this.contactFormService = contactFormService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactFormDTO>> getForms(String username) {
        if(userService.findUserByUsername(username).getAdmin()) {
            List<ContactFormDTO> response = contactFormService.getAllForms();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            List<ContactFormDTO> response = contactFormService.getFormsByUser(username);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<MessageResponseDTO> addForm(@RequestBody ContactFormRequestDTO contactDTO) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        contactFormService.addForm(contactDTO);
        messageResponseDTO.setMessage("Form added successfully");
        messageResponseDTO.setCode(0);
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<MessageResponseDTO> deleteForm(Long id) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        contactFormService.deleteForm(id);
        messageResponseDTO.setMessage("Form deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
    }
}
