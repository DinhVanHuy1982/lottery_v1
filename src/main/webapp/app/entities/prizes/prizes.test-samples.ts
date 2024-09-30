import dayjs from 'dayjs/esm';

import { IPrizes, NewPrizes } from './prizes.model';

export const sampleWithRequiredData: IPrizes = {
  id: 1256,
};

export const sampleWithPartialData: IPrizes = {
  id: 6021,
  levelCup: 'peasant',
  numberPrize: 25683,
  createTime: dayjs('2024-09-29T21:06'),
  updateTime: dayjs('2024-09-29T23:21'),
};

export const sampleWithFullData: IPrizes = {
  id: 25439,
  code: 'overhaul',
  articleCode: 'meh after',
  levelCup: 'fast render',
  numberPrize: 9637,
  createTime: dayjs('2024-09-30T01:52'),
  createName: 'really yuck',
  updateTime: dayjs('2024-09-29T21:19'),
  updateName: 'polyp drat',
};

export const sampleWithNewData: NewPrizes = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
