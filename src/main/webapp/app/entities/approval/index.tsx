import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Approval from './approval';
import ApprovalDetail from './approval-detail';
import ApprovalUpdate from './approval-update';
import ApprovalDeleteDialog from './approval-delete-dialog';

const ApprovalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Approval />} />
    <Route path="new" element={<ApprovalUpdate />} />
    <Route path=":id">
      <Route index element={<ApprovalDetail />} />
      <Route path="edit" element={<ApprovalUpdate />} />
      <Route path="delete" element={<ApprovalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApprovalRoutes;
