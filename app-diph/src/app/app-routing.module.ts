import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CountryStateComponent } from './country-state/country-state.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { DistrictCycleComponent } from './district-cycle/district-cycle.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { FrontPageComponent } from './front-page/front-page.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { FeedbackFormComponent } from './feedback-form/feedback-form.component';

const routes: Routes = [
  { path: '', component: FrontPageComponent },
  // { path: '', component: CountryStateComponent },
  
  { path: 'country', component: CountryStateComponent },
  { path: 'login', component: UserLoginComponent },
  { path: 'district', component: DistrictCycleComponent }, 
  { path: 'forgotpassword', component: ForgotpasswordComponent },
  { path: 'changepassword', component: ChangepasswordComponent },
  { path: 'contactform', component: ContactFormComponent },
  { path: 'feedbackform', component: FeedbackFormComponent },
  {
    path: 'dashboard',
    component: AdminLayoutComponent,
    children: [{
      path: '',
      loadChildren: './admin-layout/admin-layout.module#AdminLayoutModule'     
    }]
  } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes,  { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const RoutingComponents = [FrontPageComponent, CountryStateComponent, UserLoginComponent, DistrictCycleComponent, ForgotpasswordComponent, ChangepasswordComponent, ContactFormComponent];

