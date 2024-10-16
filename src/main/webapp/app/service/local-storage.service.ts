import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  public readonly CURRENT_USER = 'currentUser';
  encodeItem: any;
  decodeItem: any;

  /**
   * Constructor with service injection
   * @param storage
   */
  constructor() {}

  /**
   * get data of given key
   * @param key
   */
  get(key: string): any {
    // let item = localStorage.getItem(key);
    // if (item === 'undefined') {
    //   item = null;
    // } else {
    //   // item = JSON.parse(item);
    //   if (item != null) {
    //     this.decodeItem = CryptoJS.AES.decrypt(item, environment.KEY_STORAGE);
    //     item = JSON.parse(this.decodeItem.toString(CryptoJS.enc.Utf8));
    //   }
    // }
    // return item;
  }

  decode(value: any): any {
    // if (value != null && value !== undefined) {
    //   this.decodeItem = CryptoJS.AES.decrypt(value, environment.KEY_STORAGE);
    //   value = JSON.parse(this.decodeItem.toString(CryptoJS.enc.Utf8));
    // }
    return value;
  }

  /**
   * set value on given key
   * @param key
   * @param value
   */
  set(key: string, value: any): any {
    // if (value != null) {
    //   this.encodeItem = CryptoJS.AES.encrypt(JSON.stringify(value), environment.KEY_STORAGE).toString();
    // }
    localStorage.setItem(key, this.encodeItem);
  }
}
