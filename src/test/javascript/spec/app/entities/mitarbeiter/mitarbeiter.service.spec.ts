import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MitarbeiterService } from 'app/entities/mitarbeiter/mitarbeiter.service';
import { IMitarbeiter, Mitarbeiter } from 'app/shared/model/mitarbeiter.model';

describe('Service Tests', () => {
  describe('Mitarbeiter Service', () => {
    let injector: TestBed;
    let service: MitarbeiterService;
    let httpMock: HttpTestingController;
    let elemDefault: IMitarbeiter;
    let expectedResult: IMitarbeiter | IMitarbeiter[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MitarbeiterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Mitarbeiter(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Mitarbeiter', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Mitarbeiter()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mitarbeiter', () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            vorname: 'BBBBBB',
            nachname: 'BBBBBB',
            intern: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mitarbeiter', () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            vorname: 'BBBBBB',
            nachname: 'BBBBBB',
            intern: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mitarbeiter', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
