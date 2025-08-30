// login-response.model.ts
export interface LoginResponse {
  token: string;
  email: string;
  nom: string;
  prenom: string;
  role: string;
}