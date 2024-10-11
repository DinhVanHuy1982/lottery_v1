import dayjs from 'dayjs/esm';

import { ILevelDepositsResult, NewLevelDepositsResult } from './level-deposits-result.model';

export const sampleWithRequiredData: ILevelDepositsResult = {
  id: 27102,
};

export const sampleWithPartialData: ILevelDepositsResult = {
  id: 18965,
  levelDepositsCode: 'child attentive',
  randomResultCode: 'useless',
  resultDate: dayjs('2024-09-30T02:38'),
};

export const sampleWithFullData: ILevelDepositsResult = {
  id: 8531,
  code: 'freely',
  levelDepositsCode: 'zowie during',
  randomResultCode: 'whose',
  resultDate: dayjs('2024-09-30T04:59'),
};

export const sampleWithNewData: NewLevelDepositsResult = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
