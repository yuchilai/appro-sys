import { IUser } from 'app/shared/model/user.model';
import { IWorkEntry } from 'app/shared/model/work-entry.model';
import { IEmail } from 'app/shared/model/email.model';
import { IPhoneNum } from 'app/shared/model/phone-num.model';
import { IAddress } from 'app/shared/model/address.model';
import { ITag } from 'app/shared/model/tag.model';
import { IApprover } from 'app/shared/model/approver.model';
import { THEME } from 'app/shared/model/enumerations/theme.model';

export interface IApplicationUser {
  id?: number;
  theme?: keyof typeof THEME | null;
  isOnline?: boolean | null;
  invoiceGap?: number | null;
  internalUser?: IUser | null;
  ownedWorkEntries?: IWorkEntry[] | null;
  emails?: IEmail[] | null;
  phoneNums?: IPhoneNum[] | null;
  addresses?: IAddress[] | null;
  tags?: ITag[] | null;
  approver?: IApprover | null;
}

export const defaultValue: Readonly<IApplicationUser> = {
  isOnline: false,
};
