import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HourlyRate from './hourly-rate';
import HourlyRateDetail from './hourly-rate-detail';
import HourlyRateUpdate from './hourly-rate-update';
import HourlyRateDeleteDialog from './hourly-rate-delete-dialog';

const HourlyRateRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HourlyRate />} />
    <Route path="new" element={<HourlyRateUpdate />} />
    <Route path=":id">
      <Route index element={<HourlyRateDetail />} />
      <Route path="edit" element={<HourlyRateUpdate />} />
      <Route path="delete" element={<HourlyRateDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HourlyRateRoutes;
