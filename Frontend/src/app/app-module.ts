import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { LoginComponent } from './components/login-component/login-component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { SidebarComponent } from './components/sidebar-component/sidebar-component';
import { Users } from './components/users/users';
import { DeliveryPersons } from './components/delivery-persons/delivery-persons';
import { Inventories } from './components/inventories/inventories';
import { Articles } from './components/articles/articles';

@NgModule({
  declarations: [
    App,
    DashboardComponent,
    LoginComponent,
    SidebarComponent,
    Users,
    DeliveryPersons,
    Inventories,
    Articles
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),

    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [App]
})
export class AppModule { }
