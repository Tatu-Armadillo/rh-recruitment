import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';
import { Token } from '../model/auth/Token';
import { Login } from '../model/auth/Login';
import { Response } from '../model/response/Response';
import { API } from '../config/Api';
import { Permission } from '../model/auth/Permission';
import { Create } from '../model/auth/Create';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router) { }

  user: Token = {
    userName: '',
    token: '',
    permissions: []
  }

  async login(login: Login) {
    await this.http.post<Response<Token>>(API + "auth/signin", login).forEach((res) => {
      this.storageService.setItem("Authorization", res.data.token);
      this.storageService.setItem("Bearer", res.data.token);
      this.storageService.setItem("ACCESS_TOKEN", res.data.token);
      this.user = {
        userName: res.data.userName,
        token: res.data.token,
        permissions: res.data.permissions
      }
    });

    console.log(this.user)

    if (this.verifyToken()) {
      if (this.verifyPermissions(Permission.ADMINISTRATOR)) {
        this.router.navigate(["/job"]);
      }
      else {
        alert("User not have permission")
      }
    } else {
      alert("User not found")
    }
  }

  verifyToken(): boolean {
    return this.user.token !== null
      && this.user.token !== undefined
      && this.user.token.trim() !== '';
  }

  verifyPermissions(permission: Permission): boolean {
    for (let i = 0; i < this.user.permissions.length; i++) {
      if (this.user.permissions[i] === permission) {
        return true;
      }
    }
    return false;
  }

  create(create: Create): void {
    this.http.post<Login>(API + "auth/create", create);
  }

}
