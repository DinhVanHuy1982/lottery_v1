import { IArticles, NewArticles } from './articles.model';

export const sampleWithRequiredData: IArticles = {
  id: 29305,
  code: 'gall towards while',
};

export const sampleWithPartialData: IArticles = {
  id: 20800,
  code: 'murky brr truthfully',
  fileId: 'joshingly nor mid',
  updateName: 'chromakey so',
  numberChoice: 200,
  numberOfDigits: 10153,
  timeStart: 'unsung cute',
};

export const sampleWithFullData: IArticles = {
  id: 4907,
  code: 'concerning useful',
  title: 'barring since',
  content: 'action aboard',
  fileId: 'action roast partner',
  updateName: 'brisk forebear',
  numberChoice: 624,
  numberOfDigits: 6942,
  timeStart: 'metabolite',
  timeEnd: 'imagine while underestimate',
};

export const sampleWithNewData: NewArticles = {
  code: 'alongside blister piggyback',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
