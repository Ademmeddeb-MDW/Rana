export interface DeliveryPersonResponseDto {
  idLivreur: number;
  nomLivreur: string;
  prenomLivreur: string;
  mobileLivreur: string;
  vehiculeLivreur: string;
  emailLivreur: string;
  notesLivreur: string;
  dateCreationLivreur: string;
  statutLivreur: boolean;
}

export interface DeliveryPersonDto {
  nomLivreur: string;
  prenomLivreur: string;
  mobileLivreur?: string;
  vehiculeLivreur?: string;
  emailLivreur?: string;
  notesLivreur?: string;
  statutLivreur: boolean;
}

export interface DeliveryPersonUpdateDto {
  nomLivreur?: string;
  prenomLivreur?: string;
  mobileLivreur?: string;
  vehiculeLivreur?: string;
  emailLivreur?: string;
  notesLivreur?: string;
  statutLivreur?: boolean;
}