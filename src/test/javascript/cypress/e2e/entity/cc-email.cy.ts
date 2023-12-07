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

describe('CCEmail e2e test', () => {
  const cCEmailPageUrl = '/cc-email';
  const cCEmailPageUrlPattern = new RegExp('/cc-email(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cCEmailSample = { email: '}@r+.x(\\(x' };

  let cCEmail;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/cc-emails+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/cc-emails').as('postEntityRequest');
    cy.intercept('DELETE', '/api/cc-emails/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cCEmail) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cc-emails/${cCEmail.id}`,
      }).then(() => {
        cCEmail = undefined;
      });
    }
  });

  it('CCEmails menu should load CCEmails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cc-email');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CCEmail').should('exist');
    cy.url().should('match', cCEmailPageUrlPattern);
  });

  describe('CCEmail page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cCEmailPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CCEmail page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cc-email/new$'));
        cy.getEntityCreateUpdateHeading('CCEmail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCEmailPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/cc-emails',
          body: cCEmailSample,
        }).then(({ body }) => {
          cCEmail = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/cc-emails+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/cc-emails?page=0&size=20>; rel="last",<http://localhost/api/cc-emails?page=0&size=20>; rel="first"',
              },
              body: [cCEmail],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cCEmailPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CCEmail page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cCEmail');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCEmailPageUrlPattern);
      });

      it('edit button click should load edit CCEmail page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCEmail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCEmailPageUrlPattern);
      });

      it('edit button click should load edit CCEmail page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCEmail');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCEmailPageUrlPattern);
      });

      it('last delete button click should delete instance of CCEmail', () => {
        cy.intercept('GET', '/api/cc-emails/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cCEmail').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCEmailPageUrlPattern);

        cCEmail = undefined;
      });
    });
  });

  describe('new CCEmail page', () => {
    beforeEach(() => {
      cy.visit(`${cCEmailPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CCEmail');
    });

    it('should create an instance of CCEmail', () => {
      cy.get(`[data-cy="email"]`).type('&Bz1}J@*}H_L.s(X');
      cy.get(`[data-cy="email"]`).should('have.value', '&Bz1}J@*}H_L.s(X');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cCEmail = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cCEmailPageUrlPattern);
    });
  });
});
