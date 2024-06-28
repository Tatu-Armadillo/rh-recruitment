import { Component, OnInit } from '@angular/core';
import { Job } from 'src/app/model/job/Job';
import { JobService } from 'src/app/service/job.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  constructor(private jobService: JobService) { }

  job: Job = {
    title: '',
    description: '',
    requirements: ''
  }

  ngOnInit(): void { }

  create() {
    this.jobService.create(this.job);
    this.job = {
      title: '',
      description: '',
      requirements: ''
    }
  }

}
