export interface ICentroCosto {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<ICentroCosto> = {};
