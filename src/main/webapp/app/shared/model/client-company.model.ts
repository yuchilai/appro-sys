import { IHourlyRate } from 'app/shared/model/hourly-rate.model';
import { IProjectService } from 'app/shared/model/project-service.model';
import { IContactInfo } from 'app/shared/model/contact-info.model';
import { ICCEmail } from 'app/shared/model/cc-email.model';
import { ICCPhoneNum } from 'app/shared/model/cc-phone-num.model';
import { ICCAddress } from 'app/shared/model/cc-address.model';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IAccountsPayable } from 'app/shared/model/accounts-payable.model';
import { IApprover } from 'app/shared/model/approver.model';

export interface IClientCompany {
  id?: number;
  name?: string;
  invoicePrefix?: string;
  hourlyRates?: IHourlyRate[] | null;
  projectServices?: IProjectService[] | null;
  contactInfos?: IContactInfo[] | null;
  ccEmails?: ICCEmail[] | null;
  ccPhoneNums?: ICCPhoneNum[] | null;
  ccAddresses?: ICCAddress[] | null;
  workEntries?: IWorkEntry[] | null;
  accountsPayables?: IAccountsPayable[] | null;
  approvers?: IApprover[] | null;
}

export const defaultValue: Readonly<IClientCompany> = {};
