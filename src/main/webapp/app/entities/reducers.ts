import applicationUser from 'app/entities/application-user/application-user.reducer';
import email from 'app/entities/email/email.reducer';
import address from 'app/entities/address/address.reducer';
import phoneNum from 'app/entities/phone-num/phone-num.reducer';
import clientCompany from 'app/entities/client-company/client-company.reducer';
import cCEmail from 'app/entities/cc-email/cc-email.reducer';
import cCPhoneNum from 'app/entities/cc-phone-num/cc-phone-num.reducer';
import cCAddress from 'app/entities/cc-address/cc-address.reducer';
import approver from 'app/entities/approver/approver.reducer';
import workEntry from 'app/entities/work-entry/work-entry.reducer';
import tag from 'app/entities/tag/tag.reducer';
import approval from 'app/entities/approval/approval.reducer';
import contactInfo from 'app/entities/contact-info/contact-info.reducer';
import cIEmail from 'app/entities/ci-email/ci-email.reducer';
import cIPhoneNum from 'app/entities/ci-phone-num/ci-phone-num.reducer';
import cIAddress from 'app/entities/ci-address/ci-address.reducer';
import invoice from 'app/entities/invoice/invoice.reducer';
import invoiceBillingInfo from 'app/entities/invoice-billing-info/invoice-billing-info.reducer';
import hourlyRate from 'app/entities/hourly-rate/hourly-rate.reducer';
import projectService from 'app/entities/project-service/project-service.reducer';
import accountsPayable from 'app/entities/accounts-payable/accounts-payable.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  applicationUser,
  email,
  address,
  phoneNum,
  clientCompany,
  cCEmail,
  cCPhoneNum,
  cCAddress,
  approver,
  workEntry,
  tag,
  approval,
  contactInfo,
  cIEmail,
  cIPhoneNum,
  cIAddress,
  invoice,
  invoiceBillingInfo,
  hourlyRate,
  projectService,
  accountsPayable,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
