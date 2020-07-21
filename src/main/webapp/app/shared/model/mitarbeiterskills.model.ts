import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';
import { ISkill } from 'app/shared/model/skill.model';

export interface IMitarbeiterskills {
  id?: number;
  email?: string;
  skill?: string;
  level?: number;
  email?: IMitarbeiter;
  skill?: ISkill;
  email?: IMitarbeiter;
  skill?: ISkill;
}

export class Mitarbeiterskills implements IMitarbeiterskills {
  constructor(
    public id?: number,
    public email?: string,
    public skill?: string,
    public level?: number,
    public email?: IMitarbeiter,
    public skill?: ISkill,
    public email?: IMitarbeiter,
    public skill?: ISkill
  ) {}
}
