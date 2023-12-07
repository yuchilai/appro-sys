import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IClientCompany } from 'app/shared/model/client-company.model';

export interface IHourlyRate {
  id?: number;
  name?: string;
  rate?: number;
  isDefault?: boolean;
  workEntries?: IWorkEntry[] | null;
  clientCompany?: IClientCompany | null;
}

export const defaultValue: Readonly<IHourlyRate> = {
  isDefault: false,
};
