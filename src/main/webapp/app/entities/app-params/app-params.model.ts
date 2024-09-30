export interface IAppParams {
  id: number;
  code?: string | null;
  value?: string | null;
  type?: string | null;
}

export type NewAppParams = Omit<IAppParams, 'id'> & { id: null };
