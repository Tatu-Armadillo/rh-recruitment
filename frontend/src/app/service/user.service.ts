import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, zipAll } from 'rxjs';
import { Login } from '../model/auth/Login';
import { Create } from '../model/auth/Create';
import { StorageService } from './storage.service';
import { Token } from '../model/auth/Token';
import { Response } from '../model/response/Response';
import { API } from '../config/Api';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router) { }

  login(login: Login) {
    this.http.post<Response<Token>>(API + "auth/signin", login).subscribe((res) => {
      this.storageService.setItem("Authorization", res.data.token);
      this.storageService.setItem("Bearer", res.data.token);
      this.storageService.setItem("ACCESS_TOKEN", res.data.token);
    });

    this.router.navigate(["/job"]);

  }

  create(create: Create): void {
    this.http.post<Login>(API + "auth/create", create);
  }

}
