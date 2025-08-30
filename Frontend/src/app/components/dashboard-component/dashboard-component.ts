import { Component, OnInit } from '@angular/core';
import { DashboardStats } from '../../models/dashboard-stats.model';
import { AuthService } from '../../services/auth-service';
import { DashboardService } from '../../services/dashboard-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-component',
  standalone: false,
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css'
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats | null = null;
  user: any = null;
  today: Date = new Date(); // Ajoutez cette propriété

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadStats();
  }

  loadStats(): void {
    this.dashboardService.getStats().subscribe({
      next: (data) => {
        this.stats = data;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des statistiques', error);
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']); // Ajoutez cette ligne pour rediriger vers la page de login
  }
}