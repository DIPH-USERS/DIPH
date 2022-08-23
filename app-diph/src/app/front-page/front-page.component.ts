import { Component, OnInit } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css']
})
export class FrontPageComponent implements OnInit {

  constructor(location: PlatformLocation, private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) {
    location.onPopState(() => {
      console.log("pressed back in add!!!!!");
      history.forward();
    });
  }

  ngOnInit() {
  }

  onSubmit(){
    this.router.navigate(['country']);
  }

}
