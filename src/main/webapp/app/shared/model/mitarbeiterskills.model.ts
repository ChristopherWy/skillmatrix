import { ISkill } from 'app/shared/model/skill.model';
import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';

export interface IMitarbeiterskills {
  id?: number;
  level?: number;
  skill?: ISkill;
  email?: IMitarbeiter;
}

export class Mitarbeiterskills implements IMitarbeiterskills {
  constructor(public id?: number, public level?: number, public skill?: ISkill, public email?: IMitarbeiter) {}
}
