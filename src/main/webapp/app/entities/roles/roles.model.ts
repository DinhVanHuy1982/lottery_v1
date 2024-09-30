import dayjs from 'dayjs/esm';

export interface IRoles {
  id: number;
  name?: string | null;
  code?: string | null;
  status?: number | null;
  description?: string | null;
  createTime?: dayjs.Dayjs | null;
  createName?: string | null;
  updateName?: string | null;
  updateTime?: dayjs.Dayjs | null;
}

export type NewRoles = Omit<IRoles, 'id'> & { id: null };
