import { IClientCompany } from 'app/shared/model/client-company.model';

export interface IAccountsPayable {
  id?: number;
  deptName?: string;
  repLastName?: string;
  repFirstName?: string | null;
  repEmail?: string;
  repPhoneNum?: string;
  isUsedForInvoice?: boolean;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<IAccountsPayable> = {
  isUsedForInvoice: false,
};
