import { IClientCompany } from 'app/shared/model/client-company.model';

export interface ICCAddress {
  id?: number;
  address1?: string;
  address2?: string | null;
  city?: string;
  state?: string;
  county?: string | null;
  zipCode?: string;
  country?: string | null;
  isUsedForInvoice?: boolean;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<ICCAddress> = {
  isUsedForInvoice: false,
};
