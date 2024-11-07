import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cargo from './cargo';
import CargoDetail from './cargo-detail';
import CargoUpdate from './cargo-update';
import CargoDeleteDialog from './cargo-delete-dialog';

const CargoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cargo />} />
    <Route path="new" element={<CargoUpdate />} />
    <Route path=":id">
      <Route index element={<CargoDetail />} />
      <Route path="edit" element={<CargoUpdate />} />
      <Route path="delete" element={<CargoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CargoRoutes;
