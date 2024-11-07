import dayjs from 'dayjs';
import { IPlan } from 'app/shared/model/plan.model';
import { IUnidadFuncional } from 'app/shared/model/unidad-funcional.model';
import { IRubro } from 'app/shared/model/rubro.model';
import { ICentroCosto } from 'app/shared/model/centro-costo.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { ISubPlan } from './sub-plan.model';

export interface IPresupuesto {
  id?: number;
  consecutivo?: number | null;
  descripcionActividad?: string | null;
  fechaInicio?: string | null;
  fechaFinal?: string | null;
  fechaRegistro?: dayjs.Dayjs | null;
  nombreResponsable?: string | null;
  estado?: keyof typeof Estado | null;
  correoResponsable?: string | null;
  plan?: IPlan | null;
  subPlan?: ISubPlan | null;
  unidadFuncional?: IUnidadFuncional | null;
  rubro?: IRubro | null;
  centroCosto?: ICentroCosto | null;
  observaciones?: string | null;
  anio?: string | null;
}

export const defaultValue: Readonly<IPresupuesto> = {};
