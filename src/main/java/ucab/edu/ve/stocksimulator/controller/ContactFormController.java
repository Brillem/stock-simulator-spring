package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.edu.ve.stocksimulator.dto.ContactFormDTO;
import ucab.edu.ve.stocksimulator.dto.request.ContactFormRequestDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.model.ContactForm;
import ucab.edu.ve.stocksimulator.service.ContactFormService;
import ucab.edu.ve.stocksimulator.service.EmailSenderService;
import ucab.edu.ve.stocksimulator.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactFormController {
    private final ContactFormService contactFormService;
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ContactFormController(ContactFormService contactFormService, UserService userService, EmailSenderService emailSenderService) {
        this.contactFormService = contactFormService;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
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
        ContactForm form = contactFormService.getForm(id);
        if (form != null) {
            String subject = "Ticket #" + id + " de Soporte Resuelto | Stock Simulator";
            String body = "Estimado, " + form.getUser().getFirstName() +". Nos complace informarle que su solicitud ha sido evaluada y resuelta con éxito. En Stock Simulator, nos esforzamos continuamente por ofrecer el mejor servicio posible a nuestros clientes.\n" +
                    "\n" +
                    "Saludos cordiales, El equipo de Stock Simulator";
            this.emailSenderService.sendEmail(form.getUser().getEmail(), subject, body);
        }
        contactFormService.deleteForm(id);
        messageResponseDTO.setCode(0);
        messageResponseDTO.setMessage("Form deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
    }
}
