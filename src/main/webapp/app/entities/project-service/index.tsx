import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProjectService from './project-service';
import ProjectServiceDetail from './project-service-detail';
import ProjectServiceUpdate from './project-service-update';
import ProjectServiceDeleteDialog from './project-service-delete-dialog';

const ProjectServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProjectService />} />
    <Route path="new" element={<ProjectServiceUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectServiceDetail />} />
      <Route path="edit" element={<ProjectServiceUpdate />} />
      <Route path="delete" element={<ProjectServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectServiceRoutes;
