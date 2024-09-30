import dayjs from 'dayjs/esm';

import { IArticleGroup, NewArticleGroup } from './article-group.model';

export const sampleWithRequiredData: IArticleGroup = {
  id: 14947,
  code: 'yieldingly back',
};

export const sampleWithPartialData: IArticleGroup = {
  id: 11434,
  code: 'legitimacy toward',
  updateTime: dayjs('2024-09-30T12:01'),
  fileName: 'vastly',
};

export const sampleWithFullData: IArticleGroup = {
  id: 5354,
  code: 'for against',
  title: 'smoggy times',
  mainContent: 'enormously',
  createTime: dayjs('2024-09-29T23:10'),
  updateTime: dayjs('2024-09-30T07:46'),
  createName: 'septicaemia overdue',
  updateName: 'mutter',
  fileName: 'triumph flawless royal',
  filePath: 'to',
  fileId: 'frosty',
};

export const sampleWithNewData: NewArticleGroup = {
  code: 'railroad',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
