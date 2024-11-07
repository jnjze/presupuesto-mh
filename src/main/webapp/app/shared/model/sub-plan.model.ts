import { IPlan } from 'app/shared/model/plan.model';

export interface ISubPlan {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
  plan?: IPlan | null;
}

export const defaultValue: Readonly<ISubPlan> = {};
