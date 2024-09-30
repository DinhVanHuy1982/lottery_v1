import { IFileSaves, NewFileSaves } from './file-saves.model';

export const sampleWithRequiredData: IFileSaves = {
  id: 24943,
};

export const sampleWithPartialData: IFileSaves = {
  id: 2775,
  fileName: 'tensely',
  filePath: 'sticky love',
};

export const sampleWithFullData: IFileSaves = {
  id: 10120,
  fileId: 'row so',
  fileName: 'sunday exceed',
  filePath: 'colorless quickly sharp',
};

export const sampleWithNewData: NewFileSaves = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
