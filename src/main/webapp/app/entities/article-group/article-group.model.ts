import dayjs from 'dayjs/esm';

export interface IArticleGroup {
  id: number;
  code?: string | null;
  title?: string | null;
  mainContent?: string | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
  createName?: string | null;
  updateName?: string | null;
  fileName?: string | null;
  filePath?: string | null;
  fileId?: string | null;
}

export type NewArticleGroup = Omit<IArticleGroup, 'id'> & { id: null };
