export interface ICargo {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
}

export const defaultValue: Readonly<ICargo> = {};
