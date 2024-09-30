import dayjs from 'dayjs/esm';

export interface IDeposits {
  id: number;
  articleCode?: string | null;
  netwrokCard?: string | null;
  valueCard?: string | null;
  createTime?: dayjs.Dayjs | null;
  seriCard?: string | null;
  codeCard?: string | null;
  status?: number | null;
  userAppose?: string | null;
  valueChoice?: string | null;
  phoneNumber?: string | null;
}

export type NewDeposits = Omit<IDeposits, 'id'> & { id: null };
