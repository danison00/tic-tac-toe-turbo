export interface Message{
    payload: string,
    dateHour: Date | string,
    idSender: string,
    idReceiver: string,
    type?: 'sent' | 'received'
}