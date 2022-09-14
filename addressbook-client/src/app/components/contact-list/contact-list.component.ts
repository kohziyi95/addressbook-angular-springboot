import { Contact } from './../../models';
import { Component, OnInit } from '@angular/core';
import { AddressbookService } from 'src/app/services/contact.service';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css'],
})
export class ContactListComponent implements OnInit {
  constructor(private svc: AddressbookService) {}

  ngOnInit(): void {
    this.showContact();
  }

  contactList: Contact[] = [];

  showContact() {
    this.svc.getAllContacts().subscribe((data) => {
      return (this.contactList = JSON.parse(data.data));
    });
    console.info('Contact list >>>> ', this.contactList);
  }

  deleteContact(id: string) {
    if (
      window.confirm(`Deleting Contact ID: ${id}. Please confirm to delete.`)
    ) {
      console.info('Deleting contact >>>>> ID: ', id);
      try {
        this.svc.deleteContact(id).subscribe((data) => {
          console.info('Result >>> ', data);
        });
      } catch (error) {
        console.error('Error >>> ', error);
      }
      // this.showContact();
      const item: Contact = this.contactList.find(
        (item) => item.id === id
      ) as Contact;
      this.contactList.splice(this.contactList.indexOf(item), 1);
      console.info('Contact list >>>> ', this.contactList);
    } else {
      console.info(`Delete unsuccessful. Contact ID : ${id} was not deleted.`);
      this.showContact();
    }
  }
}
