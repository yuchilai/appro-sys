import { IContactInfo } from 'app/shared/model/contact-info.model';

export interface ICIPhoneNum {
  id?: number;
  phoneNum?: string;
  contactInfo?: IContactInfo | null;
}

export const defaultValue: Readonly<ICIPhoneNum> = {};
