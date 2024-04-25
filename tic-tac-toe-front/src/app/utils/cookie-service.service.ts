import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CookieServiceService {
  constructor() {}

  getValue(name: string) {
    const cookies = document.cookie.split('; ').map((c: string) => {
      return { key: c.split('=')[0], value: c.split('=')[1] };
    });

    const cookieValue = cookies.find((cookie) => cookie.key == name)?.value;
    return cookieValue;
  }
}
