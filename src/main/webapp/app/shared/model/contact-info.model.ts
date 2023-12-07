import { ICIEmail } from 'app/shared/model/ci-email.model';
import { ICIPhoneNum } from 'app/shared/model/ci-phone-num.model';
import { ICIAddress } from 'app/shared/model/ci-address.model';
import { IClientCompany } from 'app/shared/model/client-company.model';

export interface IContactInfo {
  id?: number;
  positsion?: string;
  firstName?: string | null;
  lastName?: string;
  ciEmails?: ICIEmail[] | null;
  ciPhoneNums?: ICIPhoneNum[] | null;
  ciAddresses?: ICIAddress[] | null;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<IContactInfo> = {};
