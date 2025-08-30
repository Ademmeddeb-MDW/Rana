import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import Swal from 'sweetalert2';
import { DeliveryPersonResponseDto, DeliveryPersonDto, DeliveryPersonUpdateDto } from '../../models/delivery-person.model';
import { DeliveryPersonService } from '../../services/delivery-person-service';
import { Router } from '@angular/router';

declare var bootstrap: any;


@Component({
  selector: 'app-delivery-persons',
  standalone: false,
  templateUrl: './delivery-persons.html',
  styleUrl: './delivery-persons.css'
})
export class DeliveryPersons implements OnInit {
  user: any = null;
  deliveryPersons: DeliveryPersonResponseDto[] = [];
  loading: boolean = true;
  selectedDeliveryPerson: DeliveryPersonResponseDto | null = null;
  isEditMode: boolean = false;

  // Propriétés pour le tri
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Référence à la modal
  @ViewChild('deliveryPersonModal') modalElement!: ElementRef;
  private modal: any;

  // Formulaire
  deliveryPersonForm: DeliveryPersonDto = {
    nomLivreur: '',
    prenomLivreur: '',
    mobileLivreur: '',
    vehiculeLivreur: '',
    emailLivreur: '',
    notesLivreur: '',
    statutLivreur: true
  };

  constructor(
    private authService: AuthService,
    private deliveryPersonService: DeliveryPersonService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadDeliveryPersons();
  }

  ngAfterViewInit(): void {
    // Initialiser la modal Bootstrap après que la vue est initialisée
    this.modal = new bootstrap.Modal(this.modalElement.nativeElement);
  }

  loadDeliveryPersons(): void {
    this.loading = true;
    this.deliveryPersonService.getAllDeliveryPersons().subscribe({
      next: (data) => {
        this.deliveryPersons = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des livreurs', error);
        this.loading = false;
        Swal.fire('Erreur', 'Impossible de charger les livreurs', 'error');
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

    this.deliveryPersons.sort((a, b) => {
      let valueA = a[field as keyof DeliveryPersonResponseDto];
      let valueB = b[field as keyof DeliveryPersonResponseDto];

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
    this.deliveryPersonForm = {
      nomLivreur: '',
      prenomLivreur: '',
      mobileLivreur: '',
      vehiculeLivreur: '',
      emailLivreur: '',
      notesLivreur: '',
      statutLivreur: true
    };
    this.modal.show();
  }

  openEditModal(deliveryPerson: DeliveryPersonResponseDto): void {
    this.isEditMode = true;
    this.selectedDeliveryPerson = deliveryPerson;
    this.deliveryPersonForm = {
      nomLivreur: deliveryPerson.nomLivreur,
      prenomLivreur: deliveryPerson.prenomLivreur,
      mobileLivreur: deliveryPerson.mobileLivreur || '',
      vehiculeLivreur: deliveryPerson.vehiculeLivreur || '',
      emailLivreur: deliveryPerson.emailLivreur || '',
      notesLivreur: deliveryPerson.notesLivreur || '',
      statutLivreur: deliveryPerson.statutLivreur
    };
    this.modal.show();
  }

  saveDeliveryPerson(): void {
    if (this.isEditMode && this.selectedDeliveryPerson) {
      // Mise à jour
      const updateData: DeliveryPersonUpdateDto = {
        nomLivreur: this.deliveryPersonForm.nomLivreur,
        prenomLivreur: this.deliveryPersonForm.prenomLivreur,
        mobileLivreur: this.deliveryPersonForm.mobileLivreur,
        vehiculeLivreur: this.deliveryPersonForm.vehiculeLivreur,
        emailLivreur: this.deliveryPersonForm.emailLivreur,
        notesLivreur: this.deliveryPersonForm.notesLivreur,
        statutLivreur: this.deliveryPersonForm.statutLivreur
      };

      this.deliveryPersonService.updateDeliveryPerson(this.selectedDeliveryPerson.idLivreur, updateData).subscribe({
        next: () => {
          Swal.fire('Succès', 'Livreur modifié avec succès', 'success');
          this.loadDeliveryPersons();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la modification', error);
          Swal.fire('Erreur', 'Impossible de modifier le livreur', 'error');
        }
      });
    } else {
      // Création
      this.deliveryPersonService.createDeliveryPerson(this.deliveryPersonForm).subscribe({
        next: () => {
          Swal.fire('Succès', 'Livreur créé avec succès', 'success');
          this.loadDeliveryPersons();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la création', error);
          Swal.fire('Erreur', 'Impossible de créer le livreur', 'error');
        }
      });
    }
  }

  confirmDelete(deliveryPerson: DeliveryPersonResponseDto): void {
    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: `Voulez-vous vraiment supprimer le livreur ${deliveryPerson.prenomLivreur} ${deliveryPerson.nomLivreur}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Oui, supprimer!',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteDeliveryPerson(deliveryPerson.idLivreur);
      }
    });
  }

  deleteDeliveryPerson(id: number): void {
    this.deliveryPersonService.deleteDeliveryPerson(id).subscribe({
      next: (response) => {
        if (typeof response === 'string') {
          Swal.fire('Supprimé!', response, 'success');
        } else {
          Swal.fire('Supprimé!', 'Le livreur a été supprimé.', 'success');
        }
        this.loadDeliveryPersons();
      },
      error: (error) => {
        console.error('Erreur lors de la suppression', error);
        let errorMessage = 'Impossible de supprimer le livreur';
        
        if (error.error && typeof error.error === 'string') {
          errorMessage = error.error;
        } else if (error.message) {
          errorMessage = error.message;
        }
        
        Swal.fire('Erreur', errorMessage, 'error');
      }
    });
  }

  toggleDeliveryPersonStatus(deliveryPerson: DeliveryPersonResponseDto): void {
    this.deliveryPersonService.toggleDeliveryPersonStatus(deliveryPerson.idLivreur).subscribe({
      next: () => {
        Swal.fire('Succès', `Statut du livreur modifié`, 'success');
        this.loadDeliveryPersons();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut', error);
        Swal.fire('Erreur', 'Impossible de modifier le statut', 'error');
      }
    });
  }
}