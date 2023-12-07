import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Email from './email';
import EmailDetail from './email-detail';
import EmailUpdate from './email-update';
import EmailDeleteDialog from './email-delete-dialog';

const EmailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Email />} />
    <Route path="new" element={<EmailUpdate />} />
    <Route path=":id">
      <Route index element={<EmailDetail />} />
      <Route path="edit" element={<EmailUpdate />} />
      <Route path="delete" element={<EmailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EmailRoutes;
