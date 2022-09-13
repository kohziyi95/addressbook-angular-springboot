package com.vttp.addressbookserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.vttp.addressbookserver.models.Contact;

public interface ContactRepo extends CrudRepository<Contact, String>  {

}
