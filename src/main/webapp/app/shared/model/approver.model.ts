import { IApproval } from 'app/shared/model/approval.model';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IClientCompany } from 'app/shared/model/client-company.model';

export interface IApprover {
  id?: number;
  signatureContentType?: string | null;
  signature?: string | null;
  assignedDate?: string;
  approvals?: IApproval[] | null;
  approvedWorkEntries?: IWorkEntry[] | null;
  applicationUsers?: IApplicationUser[] | null;
  clientCompanies?: IClientCompany[] | null;
}

export const defaultValue: Readonly<IApprover> = {};
