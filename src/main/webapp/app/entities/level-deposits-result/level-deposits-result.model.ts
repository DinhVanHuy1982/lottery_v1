import dayjs from 'dayjs/esm';

export interface ILevelDepositsResult {
  id: number;
  code?: string | null;
  levelDepositsCode?: string | null;
  randomResultCode?: string | null;
  resultDate?: dayjs.Dayjs | null;
}

export type NewLevelDepositsResult = Omit<ILevelDepositsResult, 'id'> & { id: null };
