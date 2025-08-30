import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { UserResponseDto, UserDto, UserUpdateDto } from '../../models/user.model';
import { UserService } from '../../services/user-service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

declare var bootstrap: any;


@Component({
  selector: 'app-users',
  standalone: false,
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class Users implements OnInit {
  user: any = null;
  users: UserResponseDto[] = [];
  loading: boolean = true;
  selectedUser: UserResponseDto | null = null;
  isEditMode: boolean = false;

  // Propriétés pour le tri
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Référence à la modal
  @ViewChild('userModal') modalElement!: ElementRef;
  private modal: any;

  // Formulaire
  userForm: UserDto = {
    loginUser: '',
    nomUser: '',
    prenomUser: '',
    passwordUser: '',
    mail: '',
    active: true
  };

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadUsers();
  }

  ngAfterViewInit(): void {
    // Initialiser la modal Bootstrap après que la vue est initialisée
    this.modal = new bootstrap.Modal(this.modalElement.nativeElement);
  }

  loadUsers(): void {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des utilisateurs', error);
        this.loading = false;
        Swal.fire('Erreur', 'Impossible de charger les utilisateurs', 'error');
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

    this.users.sort((a, b) => {
      let valueA = a[field as keyof UserResponseDto];
      let valueB = b[field as keyof UserResponseDto];

      // Gérer les valeurs nulles ou indéfinies
      if (valueA == null) valueA = '';
      if (valueB == null) valueB = '';

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
    this.userForm = {
      loginUser: '',
      nomUser: '',
      prenomUser: '',
      passwordUser: '',
      mail: '',
      active: true
    };
    this.modal.show();
  }

  openEditModal(user: UserResponseDto): void {
    this.isEditMode = true;
    this.selectedUser = user;
    this.userForm = {
      loginUser: user.loginUser,
      nomUser: user.nomUser,
      prenomUser: user.prenomUser,
      passwordUser: '', // On laisse vide pour l'édition
      mail: user.mail,
      mobileUser: user.mobileUser || '', // Ajout de mobileUser
      siteUser: user.siteUser || '', // Ajout de siteUser
      active: user.active
    };
    this.modal.show();
  }

  saveUser(): void {
    if (this.isEditMode && this.selectedUser) {
      // Mise à jour
      const updateData: UserUpdateDto = {
        loginUser: this.userForm.loginUser,
        nomUser: this.userForm.nomUser,
        prenomUser: this.userForm.prenomUser,
        mail: this.userForm.mail,
        mobileUser: this.userForm.mobileUser,
        siteUser: this.userForm.siteUser,
        active: this.userForm.active
      };

      this.userService.updateUser(this.selectedUser.idUser, updateData).subscribe({
        next: () => {
          Swal.fire('Succès', 'Utilisateur modifié avec succès', 'success');
          this.loadUsers();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la modification', error);
          Swal.fire('Erreur', 'Impossible de modifier l\'utilisateur', 'error');
        }
      });
    } else {
      // Création
      this.userService.createUser(this.userForm).subscribe({
        next: () => {
          Swal.fire('Succès', 'Utilisateur créé avec succès', 'success');
          this.loadUsers();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la création', error);
          Swal.fire('Erreur', 'Impossible de créer l\'utilisateur', 'error');
        }
      });
    }
  }

  confirmDelete(user: UserResponseDto): void {
    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: `Voulez-vous vraiment supprimer l'utilisateur ${user.prenomUser} ${user.nomUser}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Oui, supprimer!',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteUser(user.idUser);
      }
    });
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe({
      next: (response) => {
        if (typeof response === 'string') {
          Swal.fire('Supprimé!', response, 'success');
        } else {
          Swal.fire('Supprimé!', 'L\'utilisateur a été supprimé.', 'success');
        }
        this.loadUsers();
      },
      error: (error) => {
        console.error('Erreur lors de la suppression', error);
        let errorMessage = 'Impossible de supprimer l\'utilisateur';
        
        if (error.error && typeof error.error === 'string') {
          errorMessage = error.error;
        } else if (error.message) {
          errorMessage = error.message;
        }
        
        Swal.fire('Erreur', errorMessage, 'error');
      }
    });
  }

  toggleUserStatus(user: UserResponseDto): void {
    this.userService.toggleUserStatus(user.idUser).subscribe({
      next: () => {
        Swal.fire('Succès', `Statut de l'utilisateur modifié`, 'success');
        this.loadUsers();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut', error);
        Swal.fire('Erreur', 'Impossible de modifier le statut', 'error');
      }
    });
  }
}