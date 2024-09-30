export interface IFunctions {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewFunctions = Omit<IFunctions, 'id'> & { id: null };
