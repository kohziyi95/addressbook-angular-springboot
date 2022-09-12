import { AddressbookService } from './services/contact.service';
import { Contact } from './models';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'addressbook-client';

  constructor(private svc: AddressbookService){}

  shownComponent: string = 'form';

  toggleFormComponent() {
    this.shownComponent ='form';
    console.info('current component: ', this.shownComponent);
  }

  toggleListComponent() {
    this.shownComponent = 'list';
    console.info('current component: ', this.shownComponent);
  }

  saveContact(contact: Contact){
    console.info("Saving Contact >>>>", contact);
    this.svc.saveContact(contact).then(result => {
      console.info('>>>> result: ', result)
    }).catch(error => {
      console.error('>>>> error: ', error)
    })
    this.toggleListComponent();
  }
}
