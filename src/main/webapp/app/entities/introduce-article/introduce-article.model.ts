export interface IIntroduceArticle {
  id: number;
  code?: string | null;
  articleCode?: string | null;
  title?: string | null;
  content?: string | null;
  fileId?: string | null;
}

export type NewIntroduceArticle = Omit<IIntroduceArticle, 'id'> & { id: null };
