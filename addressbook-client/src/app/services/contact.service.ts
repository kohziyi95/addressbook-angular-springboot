import { Contact, Response } from './../models';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

const ADDRESSBOOK_URL = 'http://localhost:8080/api/addressbook/';

@Injectable()
export class AddressbookService {
  constructor(private http: HttpClient) {}

  saveContact(contact: Contact): Promise<Response> {
    const headers = new HttpHeaders()
      .set('Content-type', 'application/json')
      .set('Accept', 'application/json');

    return lastValueFrom(
      this.http
        .post<Response>(ADDRESSBOOK_URL + 'save', contact, { headers })
        .pipe()
    );
  }
  // getAllContacts(contact: Contact): Promise<Response> {
  //   const headers = new HttpHeaders()
  //     .set('Content-type', 'application/json')
  //     .set('Accept', 'application/json');

  //   return lastValueFrom(
  //     this.http
  //       .post<Response>(ADDRESSBOOK_URL + 'save', contact, { headers })
  //       .pipe()
  //   );
  // }

}
