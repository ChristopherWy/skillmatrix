import { IMitarbeiter } from 'app/shared/model/mitarbeiter.model';

export interface IArbeitszeiten {
  id?: number;
  wochenstunden?: number;
  mitarbeiter?: IMitarbeiter;
}

export class Arbeitszeiten implements IArbeitszeiten {
  constructor(public id?: number, public wochenstunden?: number, public mitarbeiter?: IMitarbeiter) {}
}
