import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA   } from '@angular/core';
import {  ReactiveFormsModule } from '@angular/forms';
import {  NgbModule } from '@ng-bootstrap/ng-bootstrap';
import 'hammerjs';
import * as $ from 'jquery';

import { AppRoutingModule, RoutingComponents } from './app-routing.module';
import { ComponentsModule } from './components/components.module';
import { AppComponent } from './app.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { DiphHttpClientService } from './services/diph-http-client-service.service';
import { BasicAuthInterceptorService } from './services/basic-auth-interceptor.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {
  MatTooltipModule,
	MatButtonModule,
	MatMenuModule,
	MatToolbarModule,
	MatIconModule,
	MatCardModule,
	MatFormFieldModule,
	MatInputModule,
	MatDatepickerModule,
	MatNativeDateModule,
	MatRadioModule,
	MatSelectModule,
	MatOptionModule,
	MatSlideToggleModule,
	MatSliderModule,
	MatDialogModule
} from '@angular/material';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { FrontPageComponent } from './front-page/front-page.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';




@NgModule({
  declarations: [
    AppComponent,
    RoutingComponents,
    AdminLayoutComponent,
    FrontPageComponent,
    ForgotpasswordComponent,
    ChangepasswordComponent,
    ContactFormComponent,
    FeedbackFormComponent    
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ComponentsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatTooltipModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    MatOptionModule,
    MatSlideToggleModule,
    MatSliderModule,
    MatDialogModule,
    NgbModule,
    NgMultiSelectDropDownModule.forRoot()
  ],
  exports: [
    MatTooltipModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    MatOptionModule,
    MatSlideToggleModule,
    MatSliderModule
    ],
  providers: [DiphHttpClientService, {  
    provide:HTTP_INTERCEPTORS, useClass:BasicAuthInterceptorService, multi:true 
  }],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class AppModule { }
