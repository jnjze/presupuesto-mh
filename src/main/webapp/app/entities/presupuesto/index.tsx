import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Presupuesto from './presupuesto';
import PresupuestoDetail from './presupuesto-detail';
import PresupuestoUpdate from './presupuesto-update';
import PresupuestoDeleteDialog from './presupuesto-delete-dialog';

const PresupuestoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Presupuesto />} />
    <Route path="new" element={<PresupuestoUpdate />} />
    <Route path=":id">
      <Route index element={<PresupuestoDetail />} />
      <Route path="edit" element={<PresupuestoUpdate />} />
      <Route path="delete" element={<PresupuestoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PresupuestoRoutes;
