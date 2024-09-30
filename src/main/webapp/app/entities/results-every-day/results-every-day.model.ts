import dayjs from 'dayjs/esm';

export interface IResultsEveryDay {
  id: number;
  resultDate?: dayjs.Dayjs | null;
  prizeCode?: string | null;
  result?: string | null;
}

export type NewResultsEveryDay = Omit<IResultsEveryDay, 'id'> & { id: null };
