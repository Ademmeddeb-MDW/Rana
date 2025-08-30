import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleResponseDto, ArticleDto, ArticleUpdateDto } from '../models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/api/articles';

  constructor(private http: HttpClient) { }

  getAllArticles(): Observable<ArticleResponseDto[]> {
    return this.http.get<ArticleResponseDto[]>(this.apiUrl);
  }

  getArticleById(id: number): Observable<ArticleResponseDto> {
    return this.http.get<ArticleResponseDto>(`${this.apiUrl}/${id}`);
  }

  createArticle(article: ArticleDto): Observable<ArticleResponseDto> {
    return this.http.post<ArticleResponseDto>(this.apiUrl, article);
  }

  updateArticle(id: number, article: ArticleUpdateDto): Observable<ArticleResponseDto> {
    return this.http.put<ArticleResponseDto>(`${this.apiUrl}/${id}`, article);
  }

  deleteArticle(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      responseType: 'text'
    });
  }

  toggleArticleStatus(id: number): Observable<ArticleResponseDto> {
    return this.http.patch<ArticleResponseDto>(`${this.apiUrl}/${id}/toggle-status`, {});
  }
}