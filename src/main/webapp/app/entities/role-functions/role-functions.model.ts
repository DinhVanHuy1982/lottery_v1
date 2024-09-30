export interface IRoleFunctions {
  id: number;
  code?: string | null;
  roleCode?: string | null;
  functionCode?: string | null;
}

export type NewRoleFunctions = Omit<IRoleFunctions, 'id'> & { id: null };
