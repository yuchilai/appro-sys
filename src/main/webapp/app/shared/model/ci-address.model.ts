import { IContactInfo } from 'app/shared/model/contact-info.model';

export interface ICIAddress {
  id?: number;
  address1?: string;
  address2?: string | null;
  city?: string;
  state?: string;
  county?: string | null;
  zipCode?: string;
  country?: string | null;
  contactInfo?: IContactInfo | null;
}

export const defaultValue: Readonly<ICIAddress> = {};
