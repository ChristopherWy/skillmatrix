export interface IMitarbeiter {
  id?: number;
  email?: string;
  vorname?: string;
  nachname?: string;
  intern?: boolean;
}

export class Mitarbeiter implements IMitarbeiter {
  constructor(public id?: number, public email?: string, public vorname?: string, public nachname?: string, public intern?: boolean) {
    this.intern = this.intern || false;
  }
}
