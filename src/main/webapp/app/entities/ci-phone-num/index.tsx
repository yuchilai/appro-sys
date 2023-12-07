import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CIPhoneNum from './ci-phone-num';
import CIPhoneNumDetail from './ci-phone-num-detail';
import CIPhoneNumUpdate from './ci-phone-num-update';
import CIPhoneNumDeleteDialog from './ci-phone-num-delete-dialog';

const CIPhoneNumRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CIPhoneNum />} />
    <Route path="new" element={<CIPhoneNumUpdate />} />
    <Route path=":id">
      <Route index element={<CIPhoneNumDetail />} />
      <Route path="edit" element={<CIPhoneNumUpdate />} />
      <Route path="delete" element={<CIPhoneNumDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CIPhoneNumRoutes;
