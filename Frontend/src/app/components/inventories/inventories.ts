import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import Swal from 'sweetalert2';
import { InventoryResponseDto, InventoryDto, InventoryUpdateDto } from '../../models/inventory.model';
import { InventoryService } from '../../services/inventory-service';
import { Router } from '@angular/router';

declare var bootstrap: any;


@Component({
  selector: 'app-inventories',
  standalone: false,
  templateUrl: './inventories.html',
  styleUrl: './inventories.css'
})
export class Inventories implements OnInit {
  user: any = null;
  inventories: InventoryResponseDto[] = [];
  loading: boolean = true;
  selectedInventory: InventoryResponseDto | null = null;
  isEditMode: boolean = false;

  // Propriétés pour le tri
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Référence à la modal
  @ViewChild('inventoryModal') modalElement!: ElementRef;
  private modal: any;

  // Formulaire
  inventoryForm: InventoryDto = {
    nomInventaire: '',
    descriptionInventaire: '',
    emplacementInventaire: '',
    responsableInventaire: '',
    statutInventaire: true
  };

  constructor(
    private authService: AuthService,
    private inventoryService: InventoryService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.loadInventories();
  }

  ngAfterViewInit(): void {
    // Initialiser la modal Bootstrap après que la vue est initialisée
    this.modal = new bootstrap.Modal(this.modalElement.nativeElement);
  }

  loadInventories(): void {
    this.loading = true;
    this.inventoryService.getAllInventories().subscribe({
      next: (data) => {
        this.inventories = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des inventaires', error);
        this.loading = false;
        Swal.fire('Erreur', 'Impossible de charger les inventaires', 'error');
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

    this.inventories.sort((a, b) => {
      let valueA = a[field as keyof InventoryResponseDto];
      let valueB = b[field as keyof InventoryResponseDto];

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
    this.inventoryForm = {
      nomInventaire: '',
      descriptionInventaire: '',
      emplacementInventaire: '',
      responsableInventaire: '',
      statutInventaire: true
    };
    this.modal.show();
  }

  openEditModal(inventory: InventoryResponseDto): void {
    this.isEditMode = true;
    this.selectedInventory = inventory;
    this.inventoryForm = {
      nomInventaire: inventory.nomInventaire,
      descriptionInventaire: inventory.descriptionInventaire || '',
      emplacementInventaire: inventory.emplacementInventaire || '',
      responsableInventaire: inventory.responsableInventaire || '',
      statutInventaire: inventory.statutInventaire
    };
    this.modal.show();
  }

  saveInventory(): void {
    if (this.isEditMode && this.selectedInventory) {
      // Mise à jour
      const updateData: InventoryUpdateDto = {
        nomInventaire: this.inventoryForm.nomInventaire,
        descriptionInventaire: this.inventoryForm.descriptionInventaire,
        emplacementInventaire: this.inventoryForm.emplacementInventaire,
        responsableInventaire: this.inventoryForm.responsableInventaire,
        statutInventaire: this.inventoryForm.statutInventaire
      };

      this.inventoryService.updateInventory(this.selectedInventory.idInventaire, updateData).subscribe({
        next: () => {
          Swal.fire('Succès', 'Inventaire modifié avec succès', 'success');
          this.loadInventories();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la modification', error);
          Swal.fire('Erreur', 'Impossible de modifier l\'inventaire', 'error');
        }
      });
    } else {
      // Création
      this.inventoryService.createInventory(this.inventoryForm).subscribe({
        next: () => {
          Swal.fire('Succès', 'Inventaire créé avec succès', 'success');
          this.loadInventories();
          this.modal.hide();
        },
        error: (error) => {
          console.error('Erreur lors de la création', error);
          Swal.fire('Erreur', 'Impossible de créer l\'inventaire', 'error');
        }
      });
    }
  }

  confirmDelete(inventory: InventoryResponseDto): void {
    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: `Voulez-vous vraiment supprimer l'inventaire ${inventory.nomInventaire}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Oui, supprimer!',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteInventory(inventory.idInventaire);
      }
    });
  }

  deleteInventory(id: number): void {
    this.inventoryService.deleteInventory(id).subscribe({
      next: (response) => {
        if (typeof response === 'string') {
          Swal.fire('Supprimé!', response, 'success');
        } else {
          Swal.fire('Supprimé!', 'L\'inventaire a été supprimé.', 'success');
        }
        this.loadInventories();
      },
      error: (error) => {
        console.error('Erreur lors de la suppression', error);
        let errorMessage = 'Impossible de supprimer l\'inventaire';
        
        if (error.error && typeof error.error === 'string') {
          errorMessage = error.error;
        } else if (error.message) {
          errorMessage = error.message;
        }
        
        Swal.fire('Erreur', errorMessage, 'error');
      }
    });
  }

  toggleInventoryStatus(inventory: InventoryResponseDto): void {
    this.inventoryService.toggleInventoryStatus(inventory.idInventaire).subscribe({
      next: () => {
        Swal.fire('Succès', `Statut de l'inventaire modifié`, 'success');
        this.loadInventories();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut', error);
        Swal.fire('Erreur', 'Impossible de modifier le statut', 'error');
      }
    });
  }
}