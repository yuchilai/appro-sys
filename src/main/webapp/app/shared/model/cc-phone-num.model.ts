import { IClientCompany } from 'app/shared/model/client-company.model';

export interface ICCPhoneNum {
  id?: number;
  phoneNum?: string;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<ICCPhoneNum> = {};
