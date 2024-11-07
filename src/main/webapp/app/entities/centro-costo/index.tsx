import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CentroCosto from './centro-costo';
import CentroCostoDetail from './centro-costo-detail';
import CentroCostoUpdate from './centro-costo-update';
import CentroCostoDeleteDialog from './centro-costo-delete-dialog';

const CentroCostoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CentroCosto />} />
    <Route path="new" element={<CentroCostoUpdate />} />
    <Route path=":id">
      <Route index element={<CentroCostoDetail />} />
      <Route path="edit" element={<CentroCostoUpdate />} />
      <Route path="delete" element={<CentroCostoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CentroCostoRoutes;
