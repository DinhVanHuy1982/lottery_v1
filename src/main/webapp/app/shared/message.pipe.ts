import { Pipe, PipeTransform } from '@angular/core';
import { TYPE_MESSAGE } from '../service/utils/constants';

@Pipe({
  name: 'message',
  standalone: true,
})
export class MessagePipe implements PipeTransform {
  transform(fieldName: string, type: string, param?: string): unknown {
    if (type === TYPE_MESSAGE.REQUIRED) {
      return fieldName + ' không được để trống';
    } else if (type === TYPE_MESSAGE.MAX_LENGTH) {
      return fieldName + ' không được vượt quá ' + param + ' ký tự';
    } else if (type === TYPE_MESSAGE.MIN_LENGTH) {
      return fieldName + ' phải có tối thiểu ' + param + ' ký tự';
    } else if (type === TYPE_MESSAGE.EXIST) {
      return fieldName + ' đã tồn tại';
    } else if (type === TYPE_MESSAGE.NOT_VALID) {
      return fieldName + ' không hợp lệ';
    }
    return '';
  }
}
