import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CIAddress from './ci-address';
import CIAddressDetail from './ci-address-detail';
import CIAddressUpdate from './ci-address-update';
import CIAddressDeleteDialog from './ci-address-delete-dialog';

const CIAddressRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CIAddress />} />
    <Route path="new" element={<CIAddressUpdate />} />
    <Route path=":id">
      <Route index element={<CIAddressDetail />} />
      <Route path="edit" element={<CIAddressUpdate />} />
      <Route path="delete" element={<CIAddressDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CIAddressRoutes;
