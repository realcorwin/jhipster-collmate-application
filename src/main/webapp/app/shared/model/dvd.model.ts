import { Moment } from 'moment';

export const enum State {
  OK = 'OK',
  AWAY = 'AWAY',
  LOST = 'LOST',
  EXPECTED = 'EXPECTED',
  UNDEFINED = 'UNDEFINED'
}

export interface IDvd {
  id?: string;
  name?: string;
  releaseYear?: string;
  diskCount?: string;
  format?: string;
  lang?: string;
  state?: State;
  added?: Moment;
}

export class Dvd implements IDvd {
  constructor(
    public id?: string,
    public name?: string,
    public releaseYear?: string,
    public diskCount?: string,
    public format?: string,
    public lang?: string,
    public state?: State,
    public added?: Moment
  ) {}
}
