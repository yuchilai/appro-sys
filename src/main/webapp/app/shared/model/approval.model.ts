import { IApprover } from 'app/shared/model/approver.model';
import { IWorkEntry } from 'app/shared/model/work-entry.model';

export interface IApproval {
  id?: number;
  approved?: boolean;
  approvalDateTime?: string;
  comments?: string | null;
  approver?: IApprover | null;
  workEntry?: IWorkEntry | null;
}

export const defaultValue: Readonly<IApproval> = {
  approved: false,
};
