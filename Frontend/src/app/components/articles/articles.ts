import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import Swal from 'sweetalert2';
import { ArticleResponseDto, ArticleDto, ArticleUpdateDto } from '../../models/article.model';
import { ArticleService } from '../../services/article-service';
import { Router } from '@angular/router';

declare var bootstrap: any;


@Component({
  selector: 'app-articles',
  standalone: false,
  templateUrl: './articles.html',
  styleUrl: './articles.css'
})
export class Articles implements OnInit {
  user: any = null;
  articles: ArticleResponseDto[] = [];
  loading: boolean = true;
  selectedArticle: ArticleResponseDto | null = null;
  isEditMode: boolean = false;

  // Propriétés pour le tri
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Référence à la modal
  @ViewChild('articleModal') modalElement!: ElementRef;
  private modal: any;

  // Formulaire
  articleForm: ArticleDto = {
    codeArticle: '',
    nomArticle: '',
    descriptionArticle: '',
    categorieArticle: '',
    prixArticle: 0,
    quantiteArticle: 0,
    seuilAlerteArticle: 0,
    statutArticle: true
  };

  constructor(
    private authService: AuthService,
    private articleService: ArticleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadArticles();
  }

  ngAfterViewInit(): void {
    // Initialiser la modal Bootstrap après que la vue est initialisée
    this.modal = new bootstrap.Modal(this.modalElement.nativeElement);
  }

  loadArticles(): void {
    this.loading = true;
    this.articleService.getAllArticles().subscribe({
      next: (data) => {
        this.articles = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des articles', error);
        this.loading = false;
        Swal.fire('Erreur', 'Impossible de charger les articles', 'error');
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']); // Ajoutez cette ligne pour rediriger vers la page de login
  }

  // Méthode de tri
  sort(field: string): void {
    if (this.sortField === field) {
      // Inverser la direction si le même champ est cliqué à nouveau
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      // Nouveau champ, tri ascendant par défaut
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.articles.sort((a, b) => {
      let valueA = a[field as keyof ArticleResponseDto];
      let valueB = b[field as keyof ArticleResponseDto];

      // Gérer les valeurs nulles ou indéfinies
      if (valueA == null) valueA = '';
      if (valueB == null) valueB = '';

      // Pour les champs numériques (prix, quantité, seuil)
      if (field === 'prixArticle' || field === 'quantiteArticle' || field === 'seuilAlerteArticle') {
        valueA = valueA || 0;
        valueB = valueB || 0;
      }

      // Comparaison des valeurs
      if (valueA < valueB) {
        return this.sortDirection === 'asc' ? -1 : 1;
      } else if (valueA > valueB) {
        return this.sortDirection === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });
  }

  // Méthode pour obtenir la classe de l'icône de tri
  getSortIconClass(field: string): string {
    if (this.sortField !== field) {
      return 'fas fa-sort';
    }
    return this.sortDirection === 'asc' ? 'fas fa-sort-up' : 'fas fa-sort-down';
  }

  openAddModal(): void {
    this.isEditMode = false;
    this.articleForm = {
      codeArticle: '',
      nomArticle: '',
      descriptionArticle: '',
      categorieArticle: '',
      prixArticle: 0,
      quantiteArticle: 0,
      seuilAlerteArticle: 0,
      statutArticle: true
    };
    this.modal.show();
  }

  openEditModal(article: ArticleResponseDto): void {
    this.isEditMode = true;
    this.selectedArticle = article;
    this.articleForm = {
      codeArticle: article.codeArticle,
      nomArticle: article.nomArticle,
      descriptionArticle: article.descriptionArticle || '',
      categorieArticle: article.categorieArticle || '',
      prixArticle: article.prixArticle || 0,
      quantiteArticle: article.quantiteArticle || 0,
      seuilAlerteArticle: article.seuilAlerteArticle || 0,
      statutArticle: article.statutArticle
    };
    this.modal.show();
  }

  saveArticle(): void {
    if (this.isEditMode && this.selectedArticle) {
      // Mise à jour
      const updateData: ArticleUpdateDto = {
        codeArticle: this.articleForm.codeArticle,
        nomArticle: this.articleForm.nomArticle,
        descriptionArticle: this.articleForm.descriptionArticle,
        categorieArticle: this.articleForm.categorieArticle,
        prixArticle: this.articleForm.prixArticle,
        quantiteArticle: this.articleForm.quantiteArticle,
        seuilAlerteArticle: this.articleForm.seuilAlerteArticle,
        statutArticle: this.articleForm.statutArticle
      };

      this.articleService.updateArticle(this.selectedArticle.idArticle, updateData).subscribe({
        next: () => {
          Swal.fire('Succès', 'Article modifié avec succès', 'success');
          this.loadArticles();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la modification', error);
          Swal.fire('Erreur', 'Impossible de modifier l\'article', 'error');
        }
      });
    } else {
      // Création
      this.articleService.createArticle(this.articleForm).subscribe({
        next: () => {
          Swal.fire('Succès', 'Article créé avec succès', 'success');
          this.loadArticles();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la création', error);
          Swal.fire('Erreur', 'Impossible de créer l\'article', 'error');
        }
      });
    }
  }

  confirmDelete(article: ArticleResponseDto): void {
    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: `Voulez-vous vraiment supprimer l'article ${article.nomArticle}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Oui, supprimer!',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteArticle(article.idArticle);
      }
    });
  }

  deleteArticle(id: number): void {
    this.articleService.deleteArticle(id).subscribe({
      next: (response) => {
        if (typeof response === 'string') {
          Swal.fire('Supprimé!', response, 'success');
        } else {
          Swal.fire('Supprimé!', 'L\'article a été supprimé.', 'success');
        }
        this.loadArticles();
      },
      error: (error) => {
        console.error('Erreur lors de la suppression', error);
        let errorMessage = 'Impossible de supprimer l\'article';
        
        if (error.error && typeof error.error === 'string') {
          errorMessage = error.error;
        } else if (error.message) {
          errorMessage = error.message;
        }
        
        Swal.fire('Erreur', errorMessage, 'error');
      }
    });
  }

  toggleArticleStatus(article: ArticleResponseDto): void {
    this.articleService.toggleArticleStatus(article.idArticle).subscribe({
      next: () => {
        Swal.fire('Succès', `Statut de l'article modifié`, 'success');
        this.loadArticles();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut', error);
        Swal.fire('Erreur', 'Impossible de modifier le statut', 'error');
      }
    });
  }
}