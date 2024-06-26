import { Component, OnInit } from '@angular/core';
import { Login } from 'src/app/model/auth/Login';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void { }

  loginForm: Login = {
    userName: '',
    password: ''
  }

  login() {
    this.userService.login(this.loginForm);
  }

}
