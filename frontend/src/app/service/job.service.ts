import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Job } from '../model/job/Job';
import { Response } from '../model/response/Response';
import { API } from '../config/Api';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(
    private http: HttpClient,
    private storageService: StorageService) { }

  create(job: Job) {
    const token = this.storageService.getItem('Authorization');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    this.http.post<Response<Job>>(API + "job", job, { headers }).subscribe((res) => {
      alert(`Job ${res.data.title} criado`)
    })
  }

}
