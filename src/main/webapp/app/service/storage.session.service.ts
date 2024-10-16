import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
// import * as CryptoJS from 'crypto-js';
import { environment } from './utils/constants';

@Injectable({ providedIn: 'root' })
export class StorageSessionService {
  encodeItem: any;
  decodeItem: any;
  private key = 'QLNBT';
  private storage: Storage;
  // subjects = new Map<string, BehaviorSubject<any>>();

  /**
   * Constructor with service injection
   * @param storage
   */
  constructor() {
    this.storage = sessionStorage;
  }

  /**
   * get data of given key
   * @param key
   */
  get(key: string): any {
    let item = this.storage.getItem(key);
    if (item === 'undefined') {
      item = null;
    } else {
      // item = JSON.parse(item);
      if (item != null) {
        // this.decodeItem = CryptoJS.AES.decrypt(item, environment.KEY_STORAGE);
        // item = JSON.parse(this.decodeItem.toString(CryptoJS.enc.Utf8));
      }
    }
    return item;
  }

  decode(value: any): any {
    if (value != null && value !== undefined) {
      // this.decodeItem = CryptoJS.AES.decrypt(value, environment.KEY_STORAGE);
      // value = JSON.parse(this.decodeItem.toString(CryptoJS.enc.Utf8));
    }
    return value;
  }

  /**
   * set value on given key
   * @param key
   * @param value
   */
  set(key: string, value: any): void {
    // if (value != null) {
    //   // this.encodeItem = CryptoJS.AES.encrypt(JSON.stringify(value), environment.KEY_STORAGE).toString();
    // }
    // this.storage.setItem(key, this.encodeItem);
    // if (!this.subjects.has(key)) {
    //   this.subjects.set(key, new BehaviorSubject<any>(value));
    // } else {
    //   this.subjects.get(key)?.next(value);
    // }
  }

  /**
   * remove given key
   * @param key
   */
  remove(key: string): void {
    // if (this.subjects.has(key)) {
    //   this.subjects.get(key)?.complete();
    //   this.subjects.delete(key);
    // }
    // this.storage.removeItem(key);
  }

  /**
   * clear all available keys
   */
  clear(): void {
    // this.subjects.clear();
    // this.storage.clear();
  }
}
