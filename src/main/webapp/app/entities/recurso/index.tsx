import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Recurso from './recurso';
import RecursoDetail from './recurso-detail';
import RecursoUpdate from './recurso-update';
import RecursoDeleteDialog from './recurso-delete-dialog';

const RecursoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Recurso />} />
    <Route path="new" element={<RecursoUpdate />} />
    <Route path=":id">
      <Route index element={<RecursoDetail />} />
      <Route path="edit" element={<RecursoUpdate />} />
      <Route path="delete" element={<RecursoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RecursoRoutes;
