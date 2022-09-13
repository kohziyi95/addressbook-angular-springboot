package com.vttp.addressbookserver.controllers;

import java.io.StringReader;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vttp.addressbookserver.models.Contact;
import com.vttp.addressbookserver.models.Response;
import com.vttp.addressbookserver.repository.ContactRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
// @CrossOrigin(origins="*")
@RequestMapping(path = "api/addressbook", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressbookRestController {
    Logger logger = Logger.getLogger(AddressbookRestController.class.getName());

    @Autowired
    private ContactRepo repo;


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE )
    // @CrossOrigin(origins="*")
    public ResponseEntity<String> addContact(@RequestBody String payload){
        // logger.info("Saving contact >>>>> "+ payload.toString() );

        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObject = jsonReader.readObject();
        Contact contact;
        Response resp;

        String id = UUID.randomUUID().toString().substring(0,8);

        try {
            contact = Contact.create(jsonObject);
            contact.setId(id);
            repo.save(contact);
            logger.info("Contact saved with ID: %s".formatted(id));
        } catch (Exception e) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toJson().toString());
        }
        resp = new Response();
        resp.setCode(201);
        resp.setMessage("Contact saved with ID: %s".formatted(id));
        resp.setData(contact.toJson().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(resp.toJson().toString());
    }

    @GetMapping(value = "listContacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllContacts(){
        Iterable<Contact> allContacts =  repo.findAll();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Contact contact : allContacts) {
            arrayBuilder.add(contact.toJson());
        }
        JsonArray contactsArray = arrayBuilder.build();

        return ResponseEntity.ok(contactsArray.toString());
    }
}
