import { IInvoice } from 'app/shared/model/invoice.model';

export interface IInvoiceBillingInfo {
  id?: number;
  firstName?: string;
  lastname?: string;
  email?: string | null;
  phoneNum?: string;
  address1?: string;
  address2?: string | null;
  city?: string;
  state?: string;
  county?: string | null;
  zipCode?: string;
  country?: string | null;
  invoice?: IInvoice | null;
}

export const defaultValue: Readonly<IInvoiceBillingInfo> = {};
