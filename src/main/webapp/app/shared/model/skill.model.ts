export interface ISkill {
  id?: number;
  skill?: string;
}

export class Skill implements ISkill {
  constructor(public id?: number, public skill?: string) {}
}
