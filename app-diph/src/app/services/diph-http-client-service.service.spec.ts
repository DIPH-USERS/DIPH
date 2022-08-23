import { TestBed } from '@angular/core/testing';

import { DiphHttpClientService } from './diph-http-client-service.service';

describe('DiphHttpClientService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DiphHttpClientService = TestBed.get(DiphHttpClientService);
    expect(service).toBeTruthy();
  });
});
