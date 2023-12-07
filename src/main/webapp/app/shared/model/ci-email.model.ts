import { IContactInfo } from 'app/shared/model/contact-info.model';

export interface ICIEmail {
  id?: number;
  email?: string;
  contactInfo?: IContactInfo | null;
}

export const defaultValue: Readonly<ICIEmail> = {};
