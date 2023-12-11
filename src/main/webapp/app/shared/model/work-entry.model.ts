import { IApproval } from 'app/shared/model/approval.model';
import { IHourlyRate } from 'app/shared/model/hourly-rate.model';
import { IProjectService } from 'app/shared/model/project-service.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IClientCompany } from 'app/shared/model/client-company.model';
import { IApprover } from 'app/shared/model/approver.model';
import { ITag } from 'app/shared/model/tag.model';
import { WorkStatus } from 'app/shared/model/enumerations/work-status.model';

export interface IWorkEntry {
  id?: number;
  title?: string;
  date?: string;
  description?: string;
  startTime?: string | null;
  endTime?: string | null;
  hours?: number | null;
  status?: keyof typeof WorkStatus;
  totalAmount?: number | null;
  comment?: string | null;
  attachmentsContentType?: string | null;
  attachments?: string | null;
  fileName?: string | null;
  fileType?: string | null;
  fileSize?: number | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
  approvalKeyRegeneratedDays?: number | null;
  approvalKeyCreatedDate?: string | null;
  approvalKey?: string | null;
  batchApprovalKey?: string | null;
  approvals?: IApproval[] | null;
  hourlyRate?: IHourlyRate | null;
  projectService?: IProjectService | null;
  owner?: IApplicationUser | null;
  invoice?: IInvoice | null;
  clientCompany?: IClientCompany | null;
  approvers?: IApprover[] | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IWorkEntry> = {};
