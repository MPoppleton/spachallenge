import { Component, ViewChild } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ENTER } from '@angular/cdk/keycodes';
import { COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent, MatSnackBar } from '@angular/material';
import { SharedServices } from './services/shared.services';
import { Mail } from './dto/mail';
import { SwalComponent } from '@toverux/ngx-sweetalert2';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private services: SharedServices, public snackBar: MatSnackBar) {
    this.emails = [];
    this.cc = [];
    this.bcc = [];
  }

  @ViewChild('swalSuccess')
  private successSwal: SwalComponent;
  @ViewChild('swalError')
  private errorSwal: SwalComponent;

  // Input validators etc
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  selectable = true;
  removable = true;
  addOnBlur = true;

  // Mail Fields
  subject: string;
  messageContent: string;
  from: string;
  emails: string[];
  cc: string[];
  bcc: string[];

  // Loading
  sending: boolean;

  contentFormControl = new FormControl('', [
    Validators.required
  ]);

  fromFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);

  toFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ]);

  ccFormControl = new FormControl('', [
    Validators.email
  ]);

  bccFormControl = new FormControl('', [
    Validators.email
  ]);

  addEmail(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our email
    if ((value || '').trim()) {
      let email = value.trim();

      if (this.emailExists(email)) {
        this.openSnackError();
      } else {
        this.emails.push(value.trim());
      }
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeEmail(email: string): void {
    let index = this.emails.indexOf(email);
    if (index >= 0) {
      this.emails.splice(index, 1);
    }
  }

  addCC(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      let email = value.trim();

      if (this.emailExists(email)) {
        this.openSnackError();
      } else {
        this.cc.push(value.trim());
      }
    }

    // Reset the cc value
    if (input) {
      input.value = '';
    }
  }

  removeCC(email: string): void {
    let index = this.cc.indexOf(email);
    if (index >= 0) {
      this.cc.splice(index, 1);
    }
  }

  addBcc(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our bcc email
    if ((value || '').trim()) {
      let email = value.trim();
      if (this.emailExists(email)) {
        this.openSnackError();
      } else {
        this.bcc.push(value.trim());
      }
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeBcc(email: string): void {
    let index = this.bcc.indexOf(email);
    if (index >= 0) {
      this.bcc.splice(index, 1);
    }
  }

  emailExists(email: string): boolean {
    return (this.emails.indexOf(email) >= 0 || this.cc.indexOf(email) >= 0 || this.bcc.indexOf(email) >= 0);
  }

  matcherTo = new CustomErrorStateMatcher();
  matcherCc = new CustomErrorStateMatcher();
  matcherBcc = new CustomErrorStateMatcher();
  matcherContent = new CustomErrorStateMatcher();
  matcherFrom = new CustomErrorStateMatcher();

  sendEmail() {
    let mail = new Mail();
    mail.to = this.emails;
    mail.cc = this.cc;
    mail.bcc = this.bcc;
    mail.from = this.from;
    mail.subject = this.subject;
    mail.body = this.messageContent;

    this.sending = true;

    this.services.sendEmail(mail).toPromise()
      .then((res) => {
        this.successSwal.show().then(() => this.clearEmail());
        this.sending = false;
      })
      .catch((err) => {
        this.sending = false;
        this.errorSwal.show().then(() => this.clearEmail());
      });
  }

  openSnackError() {
    this.snackBar.open('Emails are unique to each field (To, Cc, Bcc)', 'Close', {
      duration: 4000
    });
  }

  clearEmail() {
    this.emails = [];
    this.cc = [];
    this.bcc = [];
    this.from = undefined;
    this.subject = undefined;
    this.messageContent = undefined;
    this.messageContent = undefined;
  }
}

/** Error when invalid control is dirty, touched, or submitted. */
export class CustomErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
