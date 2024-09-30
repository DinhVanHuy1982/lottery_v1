import { IIntroduceArticleGroup, NewIntroduceArticleGroup } from './introduce-article-group.model';

export const sampleWithRequiredData: IIntroduceArticleGroup = {
  id: 15274,
  code: 'before ideal',
};

export const sampleWithPartialData: IIntroduceArticleGroup = {
  id: 21852,
  code: 'tempo incidentally physically',
  articleGroupCode: 'um',
  fileId: 'uh-huh magnetise',
  titleIntroduce: 'pfft',
  contentIntroduce: 'furthermore hm imply',
};

export const sampleWithFullData: IIntroduceArticleGroup = {
  id: 13692,
  code: 'crushing declutter',
  articleGroupCode: 'station-wagon',
  fileId: 'compete gum towards',
  titleIntroduce: 'oh youthful',
  contentIntroduce: 'shrilly majestically walker',
};

export const sampleWithNewData: NewIntroduceArticleGroup = {
  code: 'recipient strong wherever',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
