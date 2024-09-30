import { IAppParams, NewAppParams } from './app-params.model';

export const sampleWithRequiredData: IAppParams = {
  id: 21884,
  code: 'republican negligee scorpion',
};

export const sampleWithPartialData: IAppParams = {
  id: 9841,
  code: 'yawningly hence populist',
  type: 'why spotted er',
};

export const sampleWithFullData: IAppParams = {
  id: 23235,
  code: 'article efficacy subtle',
  value: 'judder',
  type: 'yellow judgementally',
};

export const sampleWithNewData: NewAppParams = {
  code: 'phooey after inside',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
