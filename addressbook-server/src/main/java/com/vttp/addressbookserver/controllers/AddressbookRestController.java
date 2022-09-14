package com.vttp.addressbookserver.controllers;

import java.io.StringReader;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vttp.addressbookserver.models.Contact;
import com.vttp.addressbookserver.models.Response;
import com.vttp.addressbookserver.repository.ContactRedisRepo;
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

    @Autowired
    private ContactRedisRepo redisRepo;

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    // @CrossOrigin(origins="*")
    public ResponseEntity<String> addContact(@RequestBody String payload) {
        // logger.info("Saving contact >>>>> "+ payload.toString() );

        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObject = jsonReader.readObject();
        Contact contact;
        Response resp;

        String id = UUID.randomUUID().toString().substring(0, 8);

        try {
            contact = Contact.create(jsonObject);
            contact.setId(id);
            repo.save(contact);
            logger.info("Saved to Redis >>> " + redisRepo.save(contact).toJson().toString());
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

    @GetMapping(value = "listContacts")
    public ResponseEntity<String> getAllContacts(){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Iterable<Contact> allContacts = redisRepo.count()==0 ? repo.findAll() : redisRepo.findAll();
        for (Contact contact : allContacts) {
            arrayBuilder.add(contact.toJson());
            redisRepo.save(contact);
        }
        JsonArray contactsArray = arrayBuilder.build();
        Response resp = new Response();
        resp.setCode(201);
        resp.setMessage("Number of contacts retrieved: %s".formatted(contactsArray.size()));
        resp.setData(contactsArray.asJsonArray().toString());

        return ResponseEntity.ok(resp.toJson().toString());
    }

    @PostMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteById(@RequestBody String payload) {

        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObject = jsonReader.readObject();
        String id = jsonObject.getString("id");

        Response resp;

        try {
            repo.deleteById(id);
            redisRepo.deleteById(id);
        } catch (IllegalArgumentException e) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.toJson().toString());
        }

        resp = new Response();
        resp.setCode(200);
        resp.setMessage("Contact ID: %s deleted.".formatted(id));

        return ResponseEntity.ok().body(resp.toJson().toString());
    }
}
