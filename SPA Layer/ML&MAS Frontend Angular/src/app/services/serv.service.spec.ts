import { TestBed } from '@angular/core/testing';

import { ServService } from './serv.service';

describe('ServService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ServService = TestBed.get(ServService);
    expect(service).toBeTruthy();
  });
});
