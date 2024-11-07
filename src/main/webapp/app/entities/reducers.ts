import presupuesto from 'app/entities/presupuesto/presupuesto.reducer';
import plan from 'app/entities/plan/plan.reducer';
import subPlan from 'app/entities/sub-plan/sub-plan.reducer';
import unidadFuncional from 'app/entities/unidad-funcional/unidad-funcional.reducer';
import rubro from 'app/entities/rubro/rubro.reducer';
import centroCosto from 'app/entities/centro-costo/centro-costo.reducer';
import recurso from 'app/entities/recurso/recurso.reducer';
import tipoRecurso from 'app/entities/tipo-recurso/tipo-recurso.reducer';
import cargo from 'app/entities/cargo/cargo.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  presupuesto,
  plan,
  subPlan,
  unidadFuncional,
  rubro,
  centroCosto,
  recurso,
  tipoRecurso,
  cargo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
