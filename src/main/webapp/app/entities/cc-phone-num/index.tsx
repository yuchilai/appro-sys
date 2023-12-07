import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CCPhoneNum from './cc-phone-num';
import CCPhoneNumDetail from './cc-phone-num-detail';
import CCPhoneNumUpdate from './cc-phone-num-update';
import CCPhoneNumDeleteDialog from './cc-phone-num-delete-dialog';

const CCPhoneNumRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CCPhoneNum />} />
    <Route path="new" element={<CCPhoneNumUpdate />} />
    <Route path=":id">
      <Route index element={<CCPhoneNumDetail />} />
      <Route path="edit" element={<CCPhoneNumUpdate />} />
      <Route path="delete" element={<CCPhoneNumDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CCPhoneNumRoutes;
