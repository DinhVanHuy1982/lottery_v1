import dayjs from 'dayjs/esm';

import { ILevelDeposits, NewLevelDeposits } from './level-deposits.model';

export const sampleWithRequiredData: ILevelDeposits = {
  id: 3212,
};

export const sampleWithPartialData: ILevelDeposits = {
  id: 14299,
  code: 'beside realistic snowplow',
  minPrice: 32211,
  updateName: 'suddenly',
  articleCode: 'courtroom',
};

export const sampleWithFullData: ILevelDeposits = {
  id: 30348,
  code: 'joshingly frenetically',
  minPrice: 10221,
  updateName: 'dry',
  updateTime: dayjs('2024-09-30T12:27'),
  articleCode: 'clutch lined',
};

export const sampleWithNewData: NewLevelDeposits = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
