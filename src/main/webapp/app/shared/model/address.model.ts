import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IAddress {
  id?: number;
  address1?: string;
  address2?: string | null;
  city?: string;
  state?: string;
  county?: string | null;
  zipCode?: string;
  country?: string | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<IAddress> = {};
