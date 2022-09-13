import { Contact, Response } from './../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

// const ADDRESSBOOK_URL = 'http://localhost:8080/api/addressbook/';
const ADDRESSBOOK_URL = "https://vttpaddressbook.herokuapp.com/";

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

  getAllContacts() {
    const headers = new HttpHeaders().set('Accept', 'application/json');
    return this.http.get<Response>(ADDRESSBOOK_URL + 'listContacts', {
      headers,
    });
  }

  deleteContact(id: string) {
    const headers = new HttpHeaders().set('Accept', 'application/json');
    return this.http.post(ADDRESSBOOK_URL + 'delete', { "id": id }, {
      headers,
    });
  }
}
