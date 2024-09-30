import dayjs from 'dayjs/esm';

import { IDeposits, NewDeposits } from './deposits.model';

export const sampleWithRequiredData: IDeposits = {
  id: 8076,
};

export const sampleWithPartialData: IDeposits = {
  id: 14223,
  valueCard: 'once',
  status: 7051,
  userAppose: 'brr successfully',
  phoneNumber: 'yawningly',
};

export const sampleWithFullData: IDeposits = {
  id: 17906,
  articleCode: 'tensely',
  netwrokCard: 'solicit merrily',
  valueCard: 'roast offshore uh-huh',
  createTime: dayjs('2024-09-30T09:08'),
  seriCard: 'yahoo boohoo anxiously',
  codeCard: 'pinkie',
  status: 23095,
  userAppose: 'ah',
  valueChoice: 'near',
  phoneNumber: 'flimsy',
};

export const sampleWithNewData: NewDeposits = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
