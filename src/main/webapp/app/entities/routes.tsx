import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Presupuesto from './presupuesto';
import Plan from './plan';
import SubPlan from './sub-plan';
import UnidadFuncional from './unidad-funcional';
import Rubro from './rubro';
import CentroCosto from './centro-costo';
import Recurso from './recurso';
import TipoRecurso from './tipo-recurso';
import CargoRoutes from './cargo';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="presupuesto/*" element={<Presupuesto />} />
        <Route path="plan/*" element={<Plan />} />
        <Route path="sub-plan/*" element={<SubPlan />} />
        <Route path="unidad-funcional/*" element={<UnidadFuncional />} />
        <Route path="rubro/*" element={<Rubro />} />
        <Route path="centro-costo/*" element={<CentroCosto />} />
        <Route path="recurso/*" element={<Recurso />} />
        <Route path="tipo-recurso/*" element={<TipoRecurso />} />
        <Route path="cargo/*" element={<CargoRoutes />} />

        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
