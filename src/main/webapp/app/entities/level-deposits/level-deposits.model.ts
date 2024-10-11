import dayjs from 'dayjs/esm';

export interface ILevelDeposits {
  id: number;
  code?: string | null;
  minPrice?: number | null;
  updateName?: string | null;
  updateTime?: dayjs.Dayjs | null;
  articleCode?: string | null;
}

export type NewLevelDeposits = Omit<ILevelDeposits, 'id'> & { id: null };
