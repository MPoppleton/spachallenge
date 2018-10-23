import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Mail } from '../dto/mail';
import { Response } from '../dto/response';


@Injectable()
export class SharedServices {
  resourceUrl: string;

  constructor(private http: HttpClient) {
    this.resourceUrl = environment.server_url + '/mail';
  }

  sendEmail(email: Mail): Observable<HttpResponse<Response>> {
    return this.http.post<Response>(this.resourceUrl, email, { observe: 'response' });
  }

}
