export interface ITipoRecurso {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<ITipoRecurso> = {};
