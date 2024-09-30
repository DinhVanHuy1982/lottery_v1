import { IFunctions, NewFunctions } from './functions.model';

export const sampleWithRequiredData: IFunctions = {
  id: 4597,
};

export const sampleWithPartialData: IFunctions = {
  id: 26886,
  code: 'disastrous fluffy',
};

export const sampleWithFullData: IFunctions = {
  id: 16070,
  name: 'phooey',
  code: 'crewmen',
};

export const sampleWithNewData: NewFunctions = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
