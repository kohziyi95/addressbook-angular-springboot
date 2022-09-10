import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css'],
})
export class ContactFormComponent implements OnInit {
  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.contactForm = this.createForm();
  }

  contactForm!: FormGroup;

  createForm() {
    return this.fb.group({
      name: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      email: this.fb.control<string>('', [
        Validators.required,
        Validators.email,
      ]),
      mobile: this.fb.control<any>('', [
        Validators.required,
        Validators.pattern('[0-9]*'),
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
    });
  }

  processForm() {
    console.info('Submitting Contact Form >>>>> ', this.contactForm.value);
    this.contactForm = this.createForm();
  }

  hasError(controlName: string): boolean | undefined {
    return this.contactForm.get(controlName)?.hasError('required');
  }

  hasEmailError(controlName: string): boolean | undefined {
    return this.contactForm.get(controlName)?.hasError('email');
  }

  hasMobileError(controlName: string): boolean | undefined {
    return (
      this.contactForm.get(controlName)?.hasError('pattern') ||
      this.contactForm.get(controlName)?.errors?.['minlength'] ||
      this.contactForm.get(controlName)?.errors?.['maxlength']
    );
  }

  isDirty(controlName: string): boolean | undefined {
    return this.contactForm.get(controlName)?.dirty;
  }
}
