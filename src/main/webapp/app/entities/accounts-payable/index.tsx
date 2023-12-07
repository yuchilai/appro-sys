import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AccountsPayable from './accounts-payable';
import AccountsPayableDetail from './accounts-payable-detail';
import AccountsPayableUpdate from './accounts-payable-update';
import AccountsPayableDeleteDialog from './accounts-payable-delete-dialog';

const AccountsPayableRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AccountsPayable />} />
    <Route path="new" element={<AccountsPayableUpdate />} />
    <Route path=":id">
      <Route index element={<AccountsPayableDetail />} />
      <Route path="edit" element={<AccountsPayableUpdate />} />
      <Route path="delete" element={<AccountsPayableDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AccountsPayableRoutes;
