import { Moment } from 'moment';

export const enum State {
  OK = 'OK',
  AWAY = 'AWAY',
  LOST = 'LOST',
  EXPECTED = 'EXPECTED',
  UNDEFINED = 'UNDEFINED'
}

export interface ICd {
  id?: string;
  name?: string;
  performer?: string;
  releaseYear?: string;
  diskCount?: string;
  medium?: string;
  label?: string;
  state?: State;
  added?: Moment;
}

export class Cd implements ICd {
  constructor(
    public id?: string,
    public name?: string,
    public performer?: string,
    public releaseYear?: string,
    public diskCount?: string,
    public medium?: string,
    public label?: string,
    public state?: State,
    public added?: Moment
  ) {}
}
