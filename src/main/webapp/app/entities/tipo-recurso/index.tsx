import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TipoRecurso from './tipo-recurso';
import TipoRecursoDetail from './tipo-recurso-detail';
import TipoRecursoUpdate from './tipo-recurso-update';
import TipoRecursoDeleteDialog from './tipo-recurso-delete-dialog';

const TipoRecursoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TipoRecurso />} />
    <Route path="new" element={<TipoRecursoUpdate />} />
    <Route path=":id">
      <Route index element={<TipoRecursoDetail />} />
      <Route path="edit" element={<TipoRecursoUpdate />} />
      <Route path="delete" element={<TipoRecursoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TipoRecursoRoutes;
