import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClientCompany from './client-company';
import ClientCompanyDetail from './client-company-detail';
import ClientCompanyUpdate from './client-company-update';
import ClientCompanyDeleteDialog from './client-company-delete-dialog';

const ClientCompanyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClientCompany />} />
    <Route path="new" element={<ClientCompanyUpdate />} />
    <Route path=":id">
      <Route index element={<ClientCompanyDetail />} />
      <Route path="edit" element={<ClientCompanyUpdate />} />
      <Route path="delete" element={<ClientCompanyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClientCompanyRoutes;
