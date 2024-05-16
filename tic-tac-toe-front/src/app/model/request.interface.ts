import { WebSocketVerb } from './enums/webSocketVerb.enum';
export interface Request<T>{
    uri: string,
    verb: WebSocketVerb,
    body?: T
}