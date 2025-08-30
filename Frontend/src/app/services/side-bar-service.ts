import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SideBarService {
  private searchTermSource = new BehaviorSubject<string>('');
  private filtersSource = new BehaviorSubject<any>({
    status: 'all',
    dateRange: 'all',
    category: 'all'
  });

  currentSearchTerm = this.searchTermSource.asObservable();
  currentFilters = this.filtersSource.asObservable();

  updateSearchTerm(term: string) {
    this.searchTermSource.next(term);
  }

  updateFilters(filters: any) {
    this.filtersSource.next(filters);
  }
}