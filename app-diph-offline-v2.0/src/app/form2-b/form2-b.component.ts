import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Observable } from 'rxjs';

import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Customer } from '../model/Customer';
import { PlatformLocation } from '@angular/common';

@Component({
    selector: 'app-form2-b',
    templateUrl: './form2-b.component.html',
    styleUrls: ['./form2-b.component.css']
})
export class Form2bComponent implements OnInit {
    constructor(location: PlatformLocation,private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) { 
      history.pushState(null, null, location.href);
      location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });

    }

    @Input() customer: Customer;

    ngOnInit() {
        this.customer = new Customer();
        this.customer.id=1;
        this.customer.name="Abhishek";
        this.customer.age = 25;
        this.customer.active = true;
        this.customer.array1 = [
            {"document_val":"val1","document_availability":"val2","document_source":"val3"},
            {"document_val":"demo1","document_availability":"demo2","document_source":"demo3"}
        ];
    }


    submit(){
        alert("submitted");
        console.log(this.customer);        
    }

    //fruits.splice(2, 2);

    addnewrow(){
        this.customer.array1.push({
            "document_val":"","document_availability":"","document_source":""
          });
    }

    removecurrentrow(obj){
        alert("Position to remove is "+obj.id);
        this.customer.array1.splice(parseInt(obj.id),1);

    }

  updateActive(isActive: boolean) {
    // this.customerService.updateCustomer(this.customer.id,
    //   { name: this.customer.name, age: this.customer.age, active: isActive })
    //   .subscribe(
    //     data => {
    //       console.log(data);
    //       this.customer = data as Customer;
    //     },
    //     error => console.log(error));
  }

  deleteCustomer() {
    // this.customerService.deleteCustomer(this.customer.id)
    //   .subscribe(
    //     data => {
    //       console.log(data);
    //       this.listComponent.reloadData();
    //     },
    //     error => console.log(error));
  }

}