import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface ITag {
  id?: number;
  name?: string;
  workEntries?: IWorkEntry[] | null;
  applicationUser?: IApplicationUser | null;
}

export const defaultValue: Readonly<ITag> = {};
