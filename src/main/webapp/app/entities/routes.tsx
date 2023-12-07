import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ApplicationUser from './application-user';
import Email from './email';
import Address from './address';
import PhoneNum from './phone-num';
import ClientCompany from './client-company';
import CCEmail from './cc-email';
import CCPhoneNum from './cc-phone-num';
import CCAddress from './cc-address';
import Approver from './approver';
import WorkEntry from './work-entry';
import Tag from './tag';
import Approval from './approval';
import ContactInfo from './contact-info';
import CIEmail from './ci-email';
import CIPhoneNum from './ci-phone-num';
import CIAddress from './ci-address';
import Invoice from './invoice';
import InvoiceBillingInfo from './invoice-billing-info';
import HourlyRate from './hourly-rate';
import ProjectService from './project-service';
import AccountsPayable from './accounts-payable';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="application-user/*" element={<ApplicationUser />} />
        <Route path="email/*" element={<Email />} />
        <Route path="address/*" element={<Address />} />
        <Route path="phone-num/*" element={<PhoneNum />} />
        <Route path="client-company/*" element={<ClientCompany />} />
        <Route path="cc-email/*" element={<CCEmail />} />
        <Route path="cc-phone-num/*" element={<CCPhoneNum />} />
        <Route path="cc-address/*" element={<CCAddress />} />
        <Route path="approver/*" element={<Approver />} />
        <Route path="work-entry/*" element={<WorkEntry />} />
        <Route path="tag/*" element={<Tag />} />
        <Route path="approval/*" element={<Approval />} />
        <Route path="contact-info/*" element={<ContactInfo />} />
        <Route path="ci-email/*" element={<CIEmail />} />
        <Route path="ci-phone-num/*" element={<CIPhoneNum />} />
        <Route path="ci-address/*" element={<CIAddress />} />
        <Route path="invoice/*" element={<Invoice />} />
        <Route path="invoice-billing-info/*" element={<InvoiceBillingInfo />} />
        <Route path="hourly-rate/*" element={<HourlyRate />} />
        <Route path="project-service/*" element={<ProjectService />} />
        <Route path="accounts-payable/*" element={<AccountsPayable />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
