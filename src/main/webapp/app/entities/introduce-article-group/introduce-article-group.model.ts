export interface IIntroduceArticleGroup {
  id: number;
  code?: string | null;
  articleGroupCode?: string | null;
  fileId?: string | null;
  titleIntroduce?: string | null;
  contentIntroduce?: string | null;
}

export type NewIntroduceArticleGroup = Omit<IIntroduceArticleGroup, 'id'> & { id: null };
