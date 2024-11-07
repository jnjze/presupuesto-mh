import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rubro from './rubro';
import RubroDetail from './rubro-detail';
import RubroUpdate from './rubro-update';
import RubroDeleteDialog from './rubro-delete-dialog';

const RubroRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rubro />} />
    <Route path="new" element={<RubroUpdate />} />
    <Route path=":id">
      <Route index element={<RubroDetail />} />
      <Route path="edit" element={<RubroUpdate />} />
      <Route path="delete" element={<RubroDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RubroRoutes;
