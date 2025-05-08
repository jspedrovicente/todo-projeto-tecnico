import { Injectable } from '@angular/core';
import { Notyf } from 'notyf';

@Injectable({
  providedIn: 'root'
})
export class NotificacaoService {
  private notyf: Notyf;

  constructor() {
    this.notyf = new Notyf({
        duration: 4000,
        position: {
          x: 'right',
          y: 'top'
        }
      });
  }

  success(message: string) {
    this.notyf.open({
      type: 'success',
      message: message
    });
  }

  error(message: string) {
    this.notyf.open({
      type: 'error',
      message: message,

    });
  }

  info(message: string) {
    this.notyf.open({
      type: 'info',
      message: message
    });
  }

  warning(message: string) {
    this.notyf.open({
      type: 'warning',
      message: message,
      background: 'red'
    });
  }
}
