import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';

export interface ISkill {
  id?: number;
  skill?: string;
  mitarbeiter?: IMitarbeiter;
}

export class Skill implements ISkill {
  constructor(public id?: number, public skill?: string, public mitarbeiter?: IMitarbeiter) {}
}
