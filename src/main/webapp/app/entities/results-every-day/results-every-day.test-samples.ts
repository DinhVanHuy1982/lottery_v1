import dayjs from 'dayjs/esm';

import { IResultsEveryDay, NewResultsEveryDay } from './results-every-day.model';

export const sampleWithRequiredData: IResultsEveryDay = {
  id: 4921,
};

export const sampleWithPartialData: IResultsEveryDay = {
  id: 19774,
  resultDate: dayjs('2024-09-29T19:15'),
  prizeCode: 'misreport after',
  result: 'deliberately out why',
};

export const sampleWithFullData: IResultsEveryDay = {
  id: 2202,
  resultDate: dayjs('2024-09-29T18:33'),
  prizeCode: 'whereas politely content',
  result: 'filthy against',
};

export const sampleWithNewData: NewResultsEveryDay = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
