// import {NgbDate} from "@ng-bootstrap/ng-bootstrap";
// import {Router} from "@angular/router";
// import * as CryptoJS from 'crypto-js';
// import {environment} from '../../../../environments/environment';
// import {ApParam} from '../model/ap-param.model';
// import {DateModel} from '../model/date.model';
// import {PHONE_10_NUMBER, PHONE_9_NUMBER} from '../../../helpers/constants';
// import {ValidateInput} from '../model/validate-input.model';
// import {Action} from '../model/action.model';
// import {MenuConfig} from '../../_config/menu.config';

import { ValidateInput } from '../validate-input.model';
import { DateModel } from '../model/date.model';
import { environment } from './constants';
import dayjs from 'dayjs/esm';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
export class Actions {
  create = false;
  update = false;
  delete = false;
  search = false;
  export = false;
  import = false;
  copy = false;
}
interface Action {
  code: string;
}

export interface FunctionItem {
  code: string;
  actions: string;

  actionsItem: Action[];
}

// @ts-ignore
// @ts-ignore
export class CommonFunction {
  //
  //   static isEmpty(data: any): boolean {
  //     return data == null || data === undefined || data === '';
  //   }
  //   static stringToObjectDate(date: string): DateModel {
  //     const dateSplit = date.split('-');
  //     // tslint:disable-next-line:radix
  //     return new DateModel(parseInt(dateSplit[0]), parseInt(dateSplit[1]), parseInt(dateSplit[2]));
  //   }
  //   static convertToStringForDisplayInput(fromDate: any, toDate: any) {
  //     if (toDate) {
  //       return fromDate?.day + '/' + fromDate?.month + '/' + fromDate?.year + ' - ' + toDate?.day + '/' + toDate?.month + '/' + toDate?.year;
  //     }
  //     return fromDate?.day + '/' + fromDate?.month + '/' + fromDate?.year + ' - ';
  //   }
  //   //
  //   // static getTooltipNgSelect(items: any, bindValue: string, value, labels: string[]): string {
  //   //   if (!items || items.length === 0) {
  //   //     return ''
  //   //   }
  //   //   const item = items?.find(i => i[bindValue] === value);
  //   //   if (item) {
  //   //     return labels.map(label => item[label])?.join(' - ') ?? '';
  //   //   }
  //   //   return ''
  //   // }
  //   //
  //   // static addItalicStyleForCKEDITOR(parentDiv, parentElementCK, content) {
  //   //   // const parentElementDiv = document.getElementById(parentDiv);
  //   //   // parentElementDiv.innerHTML= this.
  //   //   const parent = document.getElementById(parentElementCK);
  //   //   const listContentItalic = parent.getElementsByTagName('i');
  //   //   const contentItalicArray = Array.prototype.slice.call(listContentItalic);
  //   //   contentItalicArray.forEach(item => {
  //   //     item.style.fontSize = '12px'
  //   //     item.style.color = 'black';
  //   //     item.style.fontWeight = 400;
  //   //   });
  //   //   parent.innerHTML = content
  //   // }
  //   //
  //   //
  //   // static openZoom(router: Router, examRoomId: number) {
  //   //   const redirectTo = `/exam-meeting/${examRoomId}`
  //   //   const url = router.serializeUrl(
  //   //     router.createUrlTree([redirectTo])
  //   //   );
  //   //
  //   //   window.open('/live/#' + url, '_blank');
  //   // }
  //   //
  //   //
  //   static trimText(text: string) {
  //     if (text !== null && text !== undefined) {
  //       text = text.toString();
  //       return text.trim();
  //     } else {
  //       return null;
  //     }
  //   }
  //   //
  //   static validateInputModel(text: string, maxLength: number, regex: any) {
  //     const result: ValidateInput = new ValidateInput();
  //     if (this.trimText(text) === null) {
  //       result.empty = true;
  //       return result;
  //     } else {
  //       text = text.toString();
  //       text = text.trim();
  //       if (text.length === 0) {
  //         result.empty = true;
  //         return result;
  //       }
  //       if (maxLength !== null) {
  //         if (text.length > maxLength) {
  //           result.maxLength = true;
  //           return result;
  //         }
  //       }
  //       if (regex !== null) {
  //         if (!text.match(regex)) {
  //           result.regex = true;
  //           return result;
  //         }
  //       }
  //     }
  //     return undefined;
  //   }
  //   static validateInput2(text: string, isEmpty: boolean, maxLength: number, regex: any) {
  //     let result: ValidateInput | undefined = new ValidateInput();
  //     if (this.validateInputModel(text, maxLength, regex) !== undefined) {
  //       result = this.validateInputModel(text, maxLength, regex);
  //     }
  //
  //     if (!isEmpty) {
  //       if (result?.empty === false && result?.maxLength === false && result?.regex === false) {
  //         result.done = true;
  //       }
  //     } else {
  //       if (result?.maxLength === false && result?.regex === false) {
  //         result.done = true;
  //       }
  //     }
  //     return result;
  //   }
  //
  //   static validateInput(text: string, maxLength: number, regex: any) {
  //     let result: ValidateInput | undefined = new ValidateInput();
  //     if (this.validateInputModel(text, maxLength, regex) !== undefined) {
  //       result = this.validateInputModel(text, maxLength, regex);
  //     }
  //
  //     if (result?.empty === false && result?.maxLength === false && result?.regex === false) {
  //       result.done = true;
  //     }
  //     return result;
  //   }
  //
  //   static validateInputUTF8SpaceModel(text: any, maxLength: any, regex: any, hasUTF8: any, hasSpace: any) {
  //     const result: ValidateInput = new ValidateInput();
  //     const regexUTF8 = /[^\u0000-\u007F]+/;
  //
  //     if (this.trimText(text) === null) {
  //       result.empty = true;
  //       return result;
  //     } else {
  //       text = text.toString();
  //       text = text.trim();
  //       if (text.length === 0) {
  //         result.empty = true;
  //         return result;
  //       }
  //       if (hasUTF8) {
  //         if (regexUTF8.test(text)) {
  //           result.UTF8 = true;
  //           return result;
  //         }
  //       }
  //       if (hasSpace) {
  //         if (text.includes(' ')) {
  //           result.space = true;
  //           return result;
  //         }
  //       }
  //       if (maxLength !== null) {
  //         if (text.length > maxLength) {
  //           result.maxLength = true;
  //           return result;
  //         }
  //       }
  //       if (regex !== null) {
  //         if (!text.match(regex)) {
  //           result.regex = true;
  //           return result;
  //         }
  //       }
  //     }
  //     return undefined;
  //   }
  //
  //   static validateInputUTF8Space(text: any, maxLength: any, regex: any, hasUTF8: any, hasSpace: any) {
  //     let result: ValidateInput | undefined = new ValidateInput();
  //     if (this.validateInputUTF8SpaceModel(text, maxLength, regex, hasUTF8, hasSpace) !== undefined) {
  //       const tmp = this.validateInputUTF8SpaceModel(text, maxLength, regex, hasUTF8, hasSpace);
  //       result = tmp ? tmp : undefined;
  //     }
  //
  //     if (
  //       result?.empty === false &&
  //       result?.UTF8 === false &&
  //       result?.space === false &&
  //       result?.maxLength === false &&
  //       result?.regex === false
  //     ) {
  //       result.done = true;
  //     }
  //     return result;
  //   }
  //
  //   // static getActionOfFunction(functionCode: string): any {
  //   //   // const listFunction = JSON.parse(sessionStorage.getItem('role')).listFunctions
  //   //   const listFunction: FunctionItem[] = JSON.parse(this.getLocalStorate('role'));
  //   //   const action: Actions = new Actions();
  //   //   if (!listFunction.length) {
  //   //     return action;
  //   //   }
  //   //   const functionDetail: any = listFunction.find(item => item.code === functionCode);
  //   //   if (!functionDetail) {
  //   //     return action;
  //   //   }
  //   //   functionDetail.actionsItem = functionDetail.actions.split(',');
  //   //   for (const a of functionDetail.actionsItem) {
  //   //     if (a.includes(environment.action.create)) {
  //   //       action.create = true;
  //   //     } else if (a.includes(environment.action.update)) {
  //   //       action.update = true;
  //   //     } else if (a.includes(environment.action.delete)) {
  //   //       action.delete = true;
  //   //     } else if (a.includes(environment.action.search)) {
  //   //       action.search = true;
  //   //     } else if (a.includes(environment.action.export)) {
  //   //       action.export = true;
  //   //     } else if (a.includes(environment.action.import)) {
  //   //       action.import = true;
  //   //     } else if (a.includes(environment.action.copy)) {
  //   //       action.copy = true;
  //   //     }
  //   //   }
  //   //
  //   //   return action;
  //   // }
  //
  //   // static getLocalStorate(key: string): any {
  //   //   let item = localStorage.getItem(key);
  //   //   if (item === 'undefined') {
  //   //     item = null;
  //   //   } else {
  //   //     // item = JSON.parse(item);
  //   //     if (item != null) {
  //   //       const decodeItem = CryptoJS.AES.decrypt(item, environment.KEY_STORAGE);
  //   //       item = JSON.parse(decodeItem.toString(CryptoJS.enc.Utf8));
  //   //     }
  //   //   }
  //   //   return item;
  //   // }
  // }
  // export function validDateValidator(): ValidatorFn {
  //   return (control: AbstractControl | any): ValidationErrors | null => {
  //     if (!control.parent) {
  //       return null;
  //     }
  //
  //     const currentValue = control.value;
  //
  //     if (currentValue) {
  //       const date = new Date(currentValue);
  //       if (date.toString() == 'Invalid Date') {
  //         return { invalidDate: true };
  //       } else {
  //         if (validDate(currentValue)) {
  //           return null;
  //         } else {
  //           return { invalidDate: true };
  //         }
  //       }
  //     }
  //     return null;
  //   };
  // }
  //
  // export function  validDate(dateString: string) {
  //   const date = new Date(dateString);
  //   const regex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;
  //   if (
  //       date.toString() != 'Invalid Date' &&
  //       (regex.test(dateString) ||
  //           dateString == date.toString() ||
  //           dateString == date.toISOString() || //"YYYY-MM-DDTHH:mm:ss[Z]"
  //           dateString == date.toISOString().replace('.000', '') || //"YYYY-MM-DDTHH:mm:ss.SSS[Z]"
  //           dateString == dayjs(dateString).format('YYYY-MM-DD HH:mm:ss.SSS') || //"YYYY-MM-DD HH:mm:ss.SSS"
  //           dateString == dayjs(dateString).format('YYYY-MM-DD HH:mm:ss')) //"YYYY-MM-DD HH:mm:ss"
  //   ) {
  //     return true;
  //   } else {
  //     return false;
  //   }
}
