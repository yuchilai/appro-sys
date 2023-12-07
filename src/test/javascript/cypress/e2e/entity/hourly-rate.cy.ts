import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('HourlyRate e2e test', () => {
  const hourlyRatePageUrl = '/hourly-rate';
  const hourlyRatePageUrlPattern = new RegExp('/hourly-rate(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const hourlyRateSample = { name: 'clever', rate: 32654.74, isDefault: false };

  let hourlyRate;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/hourly-rates+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/hourly-rates').as('postEntityRequest');
    cy.intercept('DELETE', '/api/hourly-rates/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (hourlyRate) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/hourly-rates/${hourlyRate.id}`,
      }).then(() => {
        hourlyRate = undefined;
      });
    }
  });

  it('HourlyRates menu should load HourlyRates page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('hourly-rate');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HourlyRate').should('exist');
    cy.url().should('match', hourlyRatePageUrlPattern);
  });

  describe('HourlyRate page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(hourlyRatePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HourlyRate page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/hourly-rate/new$'));
        cy.getEntityCreateUpdateHeading('HourlyRate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', hourlyRatePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/hourly-rates',
          body: hourlyRateSample,
        }).then(({ body }) => {
          hourlyRate = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/hourly-rates+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/hourly-rates?page=0&size=20>; rel="last",<http://localhost/api/hourly-rates?page=0&size=20>; rel="first"',
              },
              body: [hourlyRate],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(hourlyRatePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HourlyRate page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('hourlyRate');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', hourlyRatePageUrlPattern);
      });

      it('edit button click should load edit HourlyRate page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HourlyRate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', hourlyRatePageUrlPattern);
      });

      it('edit button click should load edit HourlyRate page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HourlyRate');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', hourlyRatePageUrlPattern);
      });

      it('last delete button click should delete instance of HourlyRate', () => {
        cy.intercept('GET', '/api/hourly-rates/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('hourlyRate').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', hourlyRatePageUrlPattern);

        hourlyRate = undefined;
      });
    });
  });

  describe('new HourlyRate page', () => {
    beforeEach(() => {
      cy.visit(`${hourlyRatePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HourlyRate');
    });

    it('should create an instance of HourlyRate', () => {
      cy.get(`[data-cy="name"]`).type('secret delay');
      cy.get(`[data-cy="name"]`).should('have.value', 'secret delay');

      cy.get(`[data-cy="rate"]`).type('18402.61');
      cy.get(`[data-cy="rate"]`).should('have.value', '18402.61');

      cy.get(`[data-cy="isDefault"]`).should('not.be.checked');
      cy.get(`[data-cy="isDefault"]`).click();
      cy.get(`[data-cy="isDefault"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        hourlyRate = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', hourlyRatePageUrlPattern);
    });
  });
});
