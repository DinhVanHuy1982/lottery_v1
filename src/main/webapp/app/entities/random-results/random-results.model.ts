import dayjs from 'dayjs/esm';

export interface IRandomResults {
  id: number;
  randomDate?: dayjs.Dayjs | null;
  prizeCode?: string | null;
  result?: string | null;
  randomUserPlay?: number | null;
  userPlay?: number | null;
  randomUserSuccess?: number | null;
  userSuccess?: number | null;
}

export type NewRandomResults = Omit<IRandomResults, 'id'> & { id: null };
