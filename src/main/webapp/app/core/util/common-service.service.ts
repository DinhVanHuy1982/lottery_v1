import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class CommonServiceService {
  constructor(
    public httpClient: HttpClient,
    private toastr: ToastrService,
  ) {}

  downloadFile(url: string, data?: any, params?: any, fileName?: string, mimeType?: any, isDownloadMessage?: boolean, handler?: any): void {
    this.httpClient
      .post(url, data, {
        observe: 'response',
        responseType: 'arraybuffer',
        params,
      })
      .pipe(finalize(() => {}))
      .subscribe(
        res => {
          handler?.onSuccess?.();
          try {
            const response = JSON.parse(new TextDecoder('utf-8').decode(res.body ?? new ArrayBuffer(0)));
            if (response.status.code === '200') {
              this.saveFile(response.data, fileName, mimeType, isDownloadMessage);
              return;
            }
            this.toastr.error(response.status.message);
          } catch {
            this.saveFile(res.body, fileName, mimeType, isDownloadMessage);
          }
        },
        error => {
          handler?.onError?.();
        },
      );
  }

  saveFile(data: any, filename?: string, mimeType?: any, isDownloadMessage?: boolean): void {
    let option = {
      type: mimeType || 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    };

    if (isDownloadMessage) {
      option = {
        type: mimeType,
      };
    }

    const blob = new Blob([data], option);

    saveAs(blob, filename);
  }
  pagination(current: number, last: number, collapse?: boolean): Array<any> {
    if (collapse) {
      return this.paginationCollapse(current, last);
    } else {
      var delta = 2,
        left = current - delta,
        right = current + delta + 1,
        range = [],
        rangeWithDots = [],
        l;

      for (let i = 1; i <= last; i++) {
        if (i == 1 || i == last || (i >= left && i < right)) {
          range.push(i);
        }
      }

      for (let i of range) {
        if (l) {
          if (i - l === 2) {
            rangeWithDots.push(l + 1);
          } else if (i - l !== 1) {
            rangeWithDots.push('...');
          }
        }
        rangeWithDots.push(i);
        l = i;
      }

      return rangeWithDots;
    }
  }

  paginationCollapse(current: number, last: number): Array<any> {
    const FIRST: number = 1;
    const delta = 1;

    const range = [];
    const rangeWithDots = [];
    let l;

    for (let i = 1; i <= last; i++) {
      if (i === 1 || i === last || i === current) {
        range.push(i);
      }
    }

    for (const i of range) {
      if (l) {
        if (i - l === 2) {
          this.pushRangWithDots(rangeWithDots, l + 1);
        } else if (i - l !== 1) {
          rangeWithDots.push('...');
        }
      }

      if (i === last && current === i && i !== FIRST) {
        this.pushRangWithDots(rangeWithDots, i - 1);
      }

      this.pushRangWithDots(rangeWithDots, i);

      if (i === FIRST && current === i && !rangeWithDots.includes('', i + 1) && i !== last) {
        this.pushRangWithDots(rangeWithDots, i + 1);
      }

      l = i;
    }

    return rangeWithDots;
  }

  pushRangWithDots(rangeWithDots: Array<any>, index: number): void {
    if (!rangeWithDots.includes(index)) {
      rangeWithDots.push(index);
    }
  }
}
