export interface IMitarbeiterskills {
  id?: number;
  email?: string;
  skill?: string;
  level?: number;
}

export class Mitarbeiterskills implements IMitarbeiterskills {
  constructor(public id?: number, public email?: string, public skill?: string, public level?: number) {}
}
