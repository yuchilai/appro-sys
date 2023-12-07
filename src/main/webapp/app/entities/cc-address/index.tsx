import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CCAddress from './cc-address';
import CCAddressDetail from './cc-address-detail';
import CCAddressUpdate from './cc-address-update';
import CCAddressDeleteDialog from './cc-address-delete-dialog';

const CCAddressRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CCAddress />} />
    <Route path="new" element={<CCAddressUpdate />} />
    <Route path=":id">
      <Route index element={<CCAddressDetail />} />
      <Route path="edit" element={<CCAddressUpdate />} />
      <Route path="delete" element={<CCAddressDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CCAddressRoutes;
