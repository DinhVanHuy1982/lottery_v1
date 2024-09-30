import dayjs from 'dayjs/esm';

import { IRoles, NewRoles } from './roles.model';

export const sampleWithRequiredData: IRoles = {
  id: 9593,
};

export const sampleWithPartialData: IRoles = {
  id: 26996,
  name: 'gorgeous ugh before',
  createName: 'abaft',
  updateName: 'generally so',
};

export const sampleWithFullData: IRoles = {
  id: 13296,
  name: 'steel daintily',
  code: 'wildly',
  status: 16164,
  description: 'fritter',
  createTime: dayjs('2024-09-29T23:35'),
  createName: 'plus',
  updateName: 'zebra lotion after',
  updateTime: dayjs('2024-09-29T18:32'),
};

export const sampleWithNewData: NewRoles = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
