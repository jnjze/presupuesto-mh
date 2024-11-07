import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Plan from './plan';
import PlanDetail from './plan-detail';
import PlanUpdate from './plan-update';
import PlanDeleteDialog from './plan-delete-dialog';

const PlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Plan />} />
    <Route path="new" element={<PlanUpdate />} />
    <Route path=":id">
      <Route index element={<PlanDetail />} />
      <Route path="edit" element={<PlanUpdate />} />
      <Route path="delete" element={<PlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanRoutes;
