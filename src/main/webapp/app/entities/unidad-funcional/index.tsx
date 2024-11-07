import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UnidadFuncional from './unidad-funcional';
import UnidadFuncionalDetail from './unidad-funcional-detail';
import UnidadFuncionalUpdate from './unidad-funcional-update';
import UnidadFuncionalDeleteDialog from './unidad-funcional-delete-dialog';

const UnidadFuncionalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UnidadFuncional />} />
    <Route path="new" element={<UnidadFuncionalUpdate />} />
    <Route path=":id">
      <Route index element={<UnidadFuncionalDetail />} />
      <Route path="edit" element={<UnidadFuncionalUpdate />} />
      <Route path="delete" element={<UnidadFuncionalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UnidadFuncionalRoutes;
