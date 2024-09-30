import dayjs from 'dayjs/esm';

import { IRandomResults, NewRandomResults } from './random-results.model';

export const sampleWithRequiredData: IRandomResults = {
  id: 7164,
};

export const sampleWithPartialData: IRandomResults = {
  id: 9641,
  randomUserPlay: 3964,
  randomUserSuccess: 11755,
  userSuccess: 21450,
};

export const sampleWithFullData: IRandomResults = {
  id: 7056,
  randomDate: dayjs('2024-09-29T20:39'),
  prizeCode: 'complicated',
  result: 'precision through',
  randomUserPlay: 150,
  userPlay: 24102,
  randomUserSuccess: 32400,
  userSuccess: 5238,
};

export const sampleWithNewData: NewRandomResults = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
