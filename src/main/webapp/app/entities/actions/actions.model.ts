export interface IActions {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewActions = Omit<IActions, 'id'> & { id: null };
