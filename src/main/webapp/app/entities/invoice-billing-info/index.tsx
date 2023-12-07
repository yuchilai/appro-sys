import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InvoiceBillingInfo from './invoice-billing-info';
import InvoiceBillingInfoDetail from './invoice-billing-info-detail';
import InvoiceBillingInfoUpdate from './invoice-billing-info-update';
import InvoiceBillingInfoDeleteDialog from './invoice-billing-info-delete-dialog';

const InvoiceBillingInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InvoiceBillingInfo />} />
    <Route path="new" element={<InvoiceBillingInfoUpdate />} />
    <Route path=":id">
      <Route index element={<InvoiceBillingInfoDetail />} />
      <Route path="edit" element={<InvoiceBillingInfoUpdate />} />
      <Route path="delete" element={<InvoiceBillingInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InvoiceBillingInfoRoutes;
