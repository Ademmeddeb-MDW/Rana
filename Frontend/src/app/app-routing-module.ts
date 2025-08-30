import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { AuthGuard } from './guards/auth.guard';
import { Users } from './components/users/users';
import { DeliveryPersons } from './components/delivery-persons/delivery-persons';
import { Inventories } from './components/inventories/inventories';
import { Articles } from './components/articles/articles';


const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'users', component: Users, canActivate: [AuthGuard] },
  { path: 'delivery-persons', component: DeliveryPersons, canActivate: [AuthGuard] },
  { path: 'inventories', component: Inventories, canActivate: [AuthGuard] },
  { path: 'articles', component: Articles, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }