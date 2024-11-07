import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SubPlan from './sub-plan';
import SubPlanDetail from './sub-plan-detail';
import SubPlanUpdate from './sub-plan-update';
import SubPlanDeleteDialog from './sub-plan-delete-dialog';

const SubPlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SubPlan />} />
    <Route path="new" element={<SubPlanUpdate />} />
    <Route path=":id">
      <Route index element={<SubPlanDetail />} />
      <Route path="edit" element={<SubPlanUpdate />} />
      <Route path="delete" element={<SubPlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubPlanRoutes;
