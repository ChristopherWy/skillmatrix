export interface IArbeitszeiten {
  id?: number;
  email?: string;
  wochenstunden?: number;
}

export class Arbeitszeiten implements IArbeitszeiten {
  constructor(public id?: number, public email?: string, public wochenstunden?: number) {}
}
