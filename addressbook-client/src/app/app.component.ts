import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'addressbook-client';

  hiddenComponent: string = 'form';

  toggleComponent() {
    this.hiddenComponent == 'form'
      ? (this.hiddenComponent = 'list')
      : (this.hiddenComponent = 'form');
    console.info('current component: ', this.hiddenComponent);
  }
}
