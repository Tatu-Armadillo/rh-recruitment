import { Component, OnInit } from '@angular/core';
import { Login } from 'src/app/model/auth/Login';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void { }

  loginForm: Login = {
    userName: '',
    password: ''
  }

  login() {
    this.authService.login(this.loginForm);
  }

}
