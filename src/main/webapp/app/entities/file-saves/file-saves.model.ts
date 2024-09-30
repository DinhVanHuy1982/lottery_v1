export interface IFileSaves {
  id: number;
  fileId?: string | null;
  fileName?: string | null;
  filePath?: string | null;
}

export type NewFileSaves = Omit<IFileSaves, 'id'> & { id: null };
