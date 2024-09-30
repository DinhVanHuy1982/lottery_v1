import { IRoleFunctionAction, NewRoleFunctionAction } from './role-function-action.model';

export const sampleWithRequiredData: IRoleFunctionAction = {
  id: 20057,
};

export const sampleWithPartialData: IRoleFunctionAction = {
  id: 24700,
  roleFunctionCode: 'joyfully oh while',
};

export const sampleWithFullData: IRoleFunctionAction = {
  id: 24279,
  roleFunctionCode: 'astrologer tremendously',
  actionCode: 'voluntarily',
};

export const sampleWithNewData: NewRoleFunctionAction = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
