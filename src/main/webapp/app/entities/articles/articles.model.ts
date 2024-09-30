export interface IArticles {
  id: number;
  code?: string | null;
  title?: string | null;
  content?: string | null;
  fileId?: string | null;
  updateName?: string | null;
  numberChoice?: number | null;
  numberOfDigits?: number | null;
  timeStart?: string | null;
  timeEnd?: string | null;
}

export type NewArticles = Omit<IArticles, 'id'> & { id: null };
