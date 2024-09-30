import { IIntroduceArticle, NewIntroduceArticle } from './introduce-article.model';

export const sampleWithRequiredData: IIntroduceArticle = {
  id: 3125,
};

export const sampleWithPartialData: IIntroduceArticle = {
  id: 28710,
  code: 'cougar distortion',
  title: 'abaft',
  content: 'up',
  fileId: 'daintily ah',
};

export const sampleWithFullData: IIntroduceArticle = {
  id: 15659,
  code: 'principal tweet bravely',
  articleCode: 'inasmuch',
  title: 'until through amid',
  content: 'upright sweltering',
  fileId: 'pleasant phooey vigorous',
};

export const sampleWithNewData: NewIntroduceArticle = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
