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

describe('CIEmail e2e test', () => {
  const cIEmailPageUrl = '/ci-email';
  const cIEmailPageUrlPattern = new RegExp('/ci-email(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cIEmailSample = { email: 'l.o@S2W.-' };

  let cIEmail;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ci-emails+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ci-emails').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ci-emails/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cIEmail) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ci-emails/${cIEmail.id}`,
      }).then(() => {
        cIEmail = undefined;
      });
    }
  });

  it('CIEmails menu should load CIEmails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ci-email');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CIEmail').should('exist');
    cy.url().should('match', cIEmailPageUrlPattern);
  });

  describe('CIEmail page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cIEmailPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CIEmail page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ci-email/new$'));
        cy.getEntityCreateUpdateHeading('CIEmail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIEmailPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ci-emails',
          body: cIEmailSample,
        }).then(({ body }) => {
          cIEmail = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ci-emails+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ci-emails?page=0&size=20>; rel="last",<http://localhost/api/ci-emails?page=0&size=20>; rel="first"',
              },
              body: [cIEmail],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cIEmailPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CIEmail page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cIEmail');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIEmailPageUrlPattern);
      });

      it('edit button click should load edit CIEmail page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIEmail');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIEmailPageUrlPattern);
      });

      it('edit button click should load edit CIEmail page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIEmail');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIEmailPageUrlPattern);
      });

      it('last delete button click should delete instance of CIEmail', () => {
        cy.intercept('GET', '/api/ci-emails/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cIEmail').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIEmailPageUrlPattern);

        cIEmail = undefined;
      });
    });
  });

  describe('new CIEmail page', () => {
    beforeEach(() => {
      cy.visit(`${cIEmailPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CIEmail');
    });

    it('should create an instance of CIEmail', () => {
      cy.get(`[data-cy="email"]`).type('4@Vo?Z.L>');
      cy.get(`[data-cy="email"]`).should('have.value', '4@Vo?Z.L>');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cIEmail = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cIEmailPageUrlPattern);
    });
  });
});
