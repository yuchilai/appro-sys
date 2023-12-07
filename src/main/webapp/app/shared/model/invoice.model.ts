import { IInvoiceBillingInfo } from 'app/shared/model/invoice-billing-info.model';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  issueDate?: string;
  amount?: number;
  status?: keyof typeof InvoiceStatus;
  paymentDueDate?: string;
  projectDescription?: string | null;
  invoiceBillingInfo?: IInvoiceBillingInfo | null;
  workEntries?: IWorkEntry[] | null;
}

export const defaultValue: Readonly<IInvoice> = {};
