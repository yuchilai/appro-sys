import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CCEmail from './cc-email';
import CCEmailDetail from './cc-email-detail';
import CCEmailUpdate from './cc-email-update';
import CCEmailDeleteDialog from './cc-email-delete-dialog';

const CCEmailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CCEmail />} />
    <Route path="new" element={<CCEmailUpdate />} />
    <Route path=":id">
      <Route index element={<CCEmailDetail />} />
      <Route path="edit" element={<CCEmailUpdate />} />
      <Route path="delete" element={<CCEmailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CCEmailRoutes;
