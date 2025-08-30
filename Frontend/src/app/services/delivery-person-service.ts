import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeliveryPersonResponseDto, DeliveryPersonDto, DeliveryPersonUpdateDto } from '../models/delivery-person.model';

@Injectable({
  providedIn: 'root'
})
export class DeliveryPersonService {
 private apiUrl = 'http://localhost:8080/api/delivery-persons';

  constructor(private http: HttpClient) { }

  getAllDeliveryPersons(): Observable<DeliveryPersonResponseDto[]> {
    return this.http.get<DeliveryPersonResponseDto[]>(this.apiUrl);
  }

  getDeliveryPersonById(id: number): Observable<DeliveryPersonResponseDto> {
    return this.http.get<DeliveryPersonResponseDto>(`${this.apiUrl}/${id}`);
  }

  createDeliveryPerson(deliveryPerson: DeliveryPersonDto): Observable<DeliveryPersonResponseDto> {
    return this.http.post<DeliveryPersonResponseDto>(this.apiUrl, deliveryPerson);
  }

  updateDeliveryPerson(id: number, deliveryPerson: DeliveryPersonUpdateDto): Observable<DeliveryPersonResponseDto> {
    return this.http.put<DeliveryPersonResponseDto>(`${this.apiUrl}/${id}`, deliveryPerson);
  }

  deleteDeliveryPerson(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }

  toggleDeliveryPersonStatus(id: number): Observable<DeliveryPersonResponseDto> {
    return this.http.patch<DeliveryPersonResponseDto>(`${this.apiUrl}/${id}/toggle-status`, {});
  }
}