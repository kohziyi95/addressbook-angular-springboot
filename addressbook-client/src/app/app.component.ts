import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'addressbook-client';

  hiddenComponent: string = 'form';

  toggleFormComponent() {
    this.hiddenComponent ='form';
    console.info('current component: ', this.hiddenComponent);
  }

  toggleListComponent() {
    this.hiddenComponent = 'list';
    console.info('current component: ', this.hiddenComponent);
  }
}
