import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  declarations: [
    FooterComponent,
    HeaderComponent,
    SidebarComponent
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
    SidebarComponent
  ]
})
export class ComponentsModule { }
