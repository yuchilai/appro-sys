import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Approver from './approver';
import ApproverDetail from './approver-detail';
import ApproverUpdate from './approver-update';
import ApproverDeleteDialog from './approver-delete-dialog';

const ApproverRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Approver />} />
    <Route path="new" element={<ApproverUpdate />} />
    <Route path=":id">
      <Route index element={<ApproverDetail />} />
      <Route path="edit" element={<ApproverUpdate />} />
      <Route path="delete" element={<ApproverDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApproverRoutes;
