import { Component, OnInit } from '@angular/core';

import { NgForm, NgModel } from '@angular/forms';
import { WorkerService } from './shared/worker.service';
import { Worker } from './shared/worker.model';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.css']
})
export class WorkersComponent implements OnInit {

  constructor(private workerService: WorkerService) { }

  onAddWorker(form: NgForm){
    if( form.invalid ){
      return;
    }
    if(form.value._id == ''){
      this.workerService.postWorker(form.value).subscribe((res) => {
        this.resetForm();
        this.refreshWorkerList()
      });
    }
    else{
      console.log(form.value)
      this.workerService.putWorker(form.value).subscribe((res) => {
      this.resetForm();
      this.refreshWorkerList()
      });
    }

  }

  resetForm(form?: NgForm){
    if(form){
      form.reset();
    }
    this.workerService.selectedWorker = {
      _id: '',
      name: '',
      age: null,
    }
    this.workerService.getWorkerList().subscribe((res) => {
      this.workerService.workers = res as Worker[];
    });
  }

  refreshWorkerList(){
    this.workerService.getWorkerList().subscribe((res) => {
      this.workerService.workers = res as Worker[];
    });
  }

  onEdit( worker: Worker){
    this.workerService.selectedWorker = worker;
  }

  onDelete( worker: Worker ){
    if(confirm('Delete this record?') == true){
        this.workerService.deleteWorker(worker._id).subscribe((res) => {
            this.refreshWorkerList();
        });
    }
  }

  ngOnInit() {
    this.refreshWorkerList();
    this.resetForm();
  }

}
