import { IArbeitszeiten } from 'app/shared/model/arbeitszeiten.model';
import { IMitarbeiterskills } from 'app/shared/model/mitarbeiterskills.model';

export interface IMitarbeiter {
  id?: number;
  email?: string;
  vorname?: string;
  nachname?: string;
  intern?: boolean;
  email?: IArbeitszeiten;
  mitarbeiterskills?: IMitarbeiterskills[];
}

export class Mitarbeiter implements IMitarbeiter {
  constructor(
    public id?: number,
    public email?: string,
    public vorname?: string,
    public nachname?: string,
    public intern?: boolean,
    public email?: IArbeitszeiten,
    public mitarbeiterskills?: IMitarbeiterskills[]
  ) {
    this.intern = this.intern || false;
  }
}
