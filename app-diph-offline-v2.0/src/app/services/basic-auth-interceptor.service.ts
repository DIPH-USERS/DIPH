import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class BasicAuthInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

   if (sessionStorage.getItem('username') && sessionStorage.getItem('authString')) {
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem('authString')
        }
      })
    }

    return next.handle(req);

  }
}
