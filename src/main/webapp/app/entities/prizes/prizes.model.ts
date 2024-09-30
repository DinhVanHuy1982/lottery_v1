import dayjs from 'dayjs/esm';

export interface IPrizes {
  id: number;
  code?: string | null;
  articleCode?: string | null;
  levelCup?: string | null;
  numberPrize?: number | null;
  createTime?: dayjs.Dayjs | null;
  createName?: string | null;
  updateTime?: dayjs.Dayjs | null;
  updateName?: string | null;
}

export type NewPrizes = Omit<IPrizes, 'id'> & { id: null };
