import { StatusCode } from './enums/status-code.enum';

export interface Response<T> {
  idSender: string;
  idReceiver: string;
  status: StatusCode;
  statusMessage: string;
  body?: T;
}
