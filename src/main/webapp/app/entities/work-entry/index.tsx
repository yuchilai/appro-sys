import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WorkEntry from './work-entry';
import WorkEntryDetail from './work-entry-detail';
import WorkEntryUpdate from './work-entry-update';
import WorkEntryDeleteDialog from './work-entry-delete-dialog';

const WorkEntryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WorkEntry />} />
    <Route path="new" element={<WorkEntryUpdate />} />
    <Route path=":id">
      <Route index element={<WorkEntryDetail />} />
      <Route path="edit" element={<WorkEntryUpdate />} />
      <Route path="delete" element={<WorkEntryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkEntryRoutes;
