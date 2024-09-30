import { IRoleFunctions, NewRoleFunctions } from './role-functions.model';

export const sampleWithRequiredData: IRoleFunctions = {
  id: 16558,
};

export const sampleWithPartialData: IRoleFunctions = {
  id: 17340,
};

export const sampleWithFullData: IRoleFunctions = {
  id: 2028,
  code: 'and flow marxism',
  roleCode: 'except',
  functionCode: 'waveform bah finished',
};

export const sampleWithNewData: NewRoleFunctions = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
