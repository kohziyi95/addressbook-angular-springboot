import { ContactListComponent } from './components/contact-list/contact-list.component';
import { AddressbookService } from './services/contact.service';
import { Contact } from './models';
import { Component, ViewChild, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'addressbook-client';

  constructor(private svc: AddressbookService) {}

  shownComponent: string = 'form';

  @ViewChild('contactList')
  contactList!: ContactListComponent;

  showAllContacts() {
    this.contactList.showContact();
  }

  ngOnInit(): void {
  }

  toggleFormComponent() {
    this.shownComponent = 'form';
    // console.info('current component: ', this.shownComponent);
  }

  toggleListComponent() {
    this.showAllContacts();
    this.shownComponent = 'list';
    // console.info('current component: ', this.shownComponent);
  }

  async saveContact(contact: Contact) {
    console.info('Saving Contact >>>>', contact);
    await this.svc
      .saveContact(contact)
      .then((result) => {
        console.info('>>>> result: ', result);
        this.toggleListComponent();
      })
      .catch((error) => {
        console.error('>>>> error: ', error);
      });
  }
}
