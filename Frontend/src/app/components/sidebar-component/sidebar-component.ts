import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar-component',
  standalone: false,
  templateUrl: './sidebar-component.html',
  styleUrl: './sidebar-component.css'
})
export class SidebarComponent {
@Output() searchTermChanged = new EventEmitter<string>();
  @Output() filtersChanged = new EventEmitter<any>();
  
  searchTerm: string = '';
  activePage: string = 'dashboard';
  
  // Filtres par défaut
  filters = {
    status: 'all',
    dateRange: 'all',
    category: 'all'
  };

  constructor(private router: Router) {}

  onSearchChange(): void {
    this.searchTermChanged.emit(this.searchTerm);
  }

  onFilterChange(): void {
    this.filtersChanged.emit(this.filters);
  }

  navigateTo(page: string): void {
    this.activePage = page;
    this.router.navigate([`/${page}`]);
  }

  isActive(page: string): boolean {
    return this.activePage === page;
  }
}