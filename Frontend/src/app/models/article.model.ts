export interface ArticleResponseDto {
  idArticle: number;
  codeArticle: string;
  nomArticle: string;
  descriptionArticle: string;
  categorieArticle: string;
  prixArticle: number;
  quantiteArticle: number;
  seuilAlerteArticle: number;
  statutArticle: boolean;
  dateCreationArticle: string;
  inventoryId: number;
  deliveryPersonId: number;
}

export interface ArticleDto {
  codeArticle: string;
  nomArticle: string;
  descriptionArticle?: string;
  categorieArticle?: string;
  prixArticle?: number;
  quantiteArticle?: number;
  seuilAlerteArticle?: number;
  statutArticle: boolean;
  inventoryId?: number;
  deliveryPersonId?: number;
}

export interface ArticleUpdateDto {
  codeArticle?: string;
  nomArticle?: string;
  descriptionArticle?: string;
  categorieArticle?: string;
  prixArticle?: number;
  quantiteArticle?: number;
  seuilAlerteArticle?: number;
  statutArticle?: boolean;
  inventoryId?: number;
  deliveryPersonId?: number;
}