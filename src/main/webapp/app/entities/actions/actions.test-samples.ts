import { IActions, NewActions } from './actions.model';

export const sampleWithRequiredData: IActions = {
  id: 5536,
};

export const sampleWithPartialData: IActions = {
  id: 1839,
};

export const sampleWithFullData: IActions = {
  id: 24149,
  name: 'beside amidst weaken',
  code: 'ack',
};

export const sampleWithNewData: NewActions = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
