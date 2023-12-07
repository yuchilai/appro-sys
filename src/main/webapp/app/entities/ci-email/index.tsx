import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CIEmail from './ci-email';
import CIEmailDetail from './ci-email-detail';
import CIEmailUpdate from './ci-email-update';
import CIEmailDeleteDialog from './ci-email-delete-dialog';

const CIEmailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CIEmail />} />
    <Route path="new" element={<CIEmailUpdate />} />
    <Route path=":id">
      <Route index element={<CIEmailDetail />} />
      <Route path="edit" element={<CIEmailUpdate />} />
      <Route path="delete" element={<CIEmailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CIEmailRoutes;
