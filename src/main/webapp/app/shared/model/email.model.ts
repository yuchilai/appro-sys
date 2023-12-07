import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IEmail {
  id?: number;
  email?: string;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<IEmail> = {};
