import { IMitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';

export interface ISkill {
  id?: number;
  skill?: string;
  mitarbeiterskills?: IMitarbeiterskills[];
}

export class Skill implements ISkill {
  constructor(public id?: number, public skill?: string, public mitarbeiterskills?: IMitarbeiterskills[]) {}
}
