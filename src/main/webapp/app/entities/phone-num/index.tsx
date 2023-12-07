import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PhoneNum from './phone-num';
import PhoneNumDetail from './phone-num-detail';
import PhoneNumUpdate from './phone-num-update';
import PhoneNumDeleteDialog from './phone-num-delete-dialog';

const PhoneNumRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PhoneNum />} />
    <Route path="new" element={<PhoneNumUpdate />} />
    <Route path=":id">
      <Route index element={<PhoneNumDetail />} />
      <Route path="edit" element={<PhoneNumUpdate />} />
      <Route path="delete" element={<PhoneNumDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PhoneNumRoutes;
