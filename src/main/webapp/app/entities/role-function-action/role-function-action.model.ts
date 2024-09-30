export interface IRoleFunctionAction {
  id: number;
  roleFunctionCode?: string | null;
  actionCode?: string | null;
}

export type NewRoleFunctionAction = Omit<IRoleFunctionAction, 'id'> & { id: null };
