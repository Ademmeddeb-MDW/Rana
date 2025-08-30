export interface InventoryResponseDto {
  idInventaire: number;
  nomInventaire: string;
  descriptionInventaire: string;
  emplacementInventaire: string;
  responsableInventaire: string;
  statutInventaire: boolean;
  dateCreationInventaire: string;
  userId: number;
}

export interface InventoryDto {
  nomInventaire: string;
  descriptionInventaire?: string;
  emplacementInventaire?: string;
  responsableInventaire?: string;
  statutInventaire: boolean;
  userId?: number;
}

export interface InventoryUpdateDto {
  nomInventaire?: string;
  descriptionInventaire?: string;
  emplacementInventaire?: string;
  responsableInventaire?: string;
  statutInventaire?: boolean;
  userId?: number;
}