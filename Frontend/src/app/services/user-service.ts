import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDto, UserResponseDto, UserUpdateDto } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService { // Correction: le nom de classe doit être 'UserService' (avec 'S' majuscule)
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<UserResponseDto[]> {
    return this.http.get<UserResponseDto[]>(this.apiUrl);
  }

  getUserById(id: number): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/${id}`);
  }

  createUser(user: UserDto): Observable<UserResponseDto> {
    return this.http.post<UserResponseDto>(this.apiUrl, user);
  }

  updateUser(id: number, user: UserUpdateDto): Observable<UserResponseDto> {
    return this.http.put<UserResponseDto>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text' // Spécifie que nous attendons une réponse texte
    });
  }

  toggleUserStatus(id: number): Observable<UserResponseDto> {
    return this.http.patch<UserResponseDto>(`${this.apiUrl}/${id}/toggle-status`, {});
  }
}