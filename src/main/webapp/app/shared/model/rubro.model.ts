export interface IRubro {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IRubro> = {};
