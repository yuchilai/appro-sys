import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IPhoneNum {
  id?: number;
  phoneNum?: string;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<IPhoneNum> = {};
