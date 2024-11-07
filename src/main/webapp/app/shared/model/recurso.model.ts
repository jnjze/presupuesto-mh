import { ITipoRecurso } from 'app/shared/model/tipo-recurso.model';
import { IPresupuesto } from 'app/shared/model/presupuesto.model';

export interface IRecurso {
  id?: number;
  mes?: string | null;
  valor?: number | null;
  observacion?: string | null;
  tipoRecurso?: ITipoRecurso | null;
  presupuesto?: IPresupuesto | null;
}

export const defaultValue: Readonly<IRecurso> = {};
