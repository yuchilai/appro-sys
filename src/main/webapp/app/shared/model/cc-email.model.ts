import { IClientCompany } from 'app/shared/model/client-company.model';

export interface ICCEmail {
  id?: number;
  email?: string;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<ICCEmail> = {};
