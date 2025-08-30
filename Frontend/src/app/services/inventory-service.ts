import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InventoryResponseDto, InventoryDto, InventoryUpdateDto } from '../models/inventory.model';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {
   private apiUrl = 'http://localhost:8080/api/inventories';

  constructor(private http: HttpClient) { }

  getAllInventories(): Observable<InventoryResponseDto[]> {
    return this.http.get<InventoryResponseDto[]>(this.apiUrl);
  }

  getInventoryById(id: number): Observable<InventoryResponseDto> {
    return this.http.get<InventoryResponseDto>(`${this.apiUrl}/${id}`);
  }

  createInventory(inventory: InventoryDto): Observable<InventoryResponseDto> {
    return this.http.post<InventoryResponseDto>(this.apiUrl, inventory);
  }

  updateInventory(id: number, inventory: InventoryUpdateDto): Observable<InventoryResponseDto> {
    return this.http.put<InventoryResponseDto>(`${this.apiUrl}/${id}`, inventory);
  }

  deleteInventory(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }

  toggleInventoryStatus(id: number): Observable<InventoryResponseDto> {
    return this.http.patch<InventoryResponseDto>(`${this.apiUrl}/${id}/toggle-status`, {});
  }
}