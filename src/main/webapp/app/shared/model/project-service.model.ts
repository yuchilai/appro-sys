import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IClientCompany } from 'app/shared/model/client-company.model';

export interface IProjectService {
  id?: number;
  name?: string;
  fee?: number;
  description?: string | null;
  dayLength?: number | null;
  extra?: string | null;
  workEntries?: IWorkEntry[] | null;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<IProjectService> = {};
