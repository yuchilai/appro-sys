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

describe('ClientCompany e2e test', () => {
  const clientCompanyPageUrl = '/client-company';
  const clientCompanyPageUrlPattern = new RegExp('/client-company(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const clientCompanySample = { name: 'rasp absolute', invoicePrefix: 'counsellor' };

  let clientCompany;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/client-companies+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/client-companies').as('postEntityRequest');
    cy.intercept('DELETE', '/api/client-companies/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (clientCompany) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/client-companies/${clientCompany.id}`,
      }).then(() => {
        clientCompany = undefined;
      });
    }
  });

  it('ClientCompanies menu should load ClientCompanies page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('client-company');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ClientCompany').should('exist');
    cy.url().should('match', clientCompanyPageUrlPattern);
  });

  describe('ClientCompany page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(clientCompanyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ClientCompany page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/client-company/new$'));
        cy.getEntityCreateUpdateHeading('ClientCompany');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientCompanyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/client-companies',
          body: clientCompanySample,
        }).then(({ body }) => {
          clientCompany = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/client-companies+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/client-companies?page=0&size=20>; rel="last",<http://localhost/api/client-companies?page=0&size=20>; rel="first"',
              },
              body: [clientCompany],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(clientCompanyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ClientCompany page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('clientCompany');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientCompanyPageUrlPattern);
      });

      it('edit button click should load edit ClientCompany page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ClientCompany');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientCompanyPageUrlPattern);
      });

      it('edit button click should load edit ClientCompany page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ClientCompany');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientCompanyPageUrlPattern);
      });

      it('last delete button click should delete instance of ClientCompany', () => {
        cy.intercept('GET', '/api/client-companies/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('clientCompany').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', clientCompanyPageUrlPattern);

        clientCompany = undefined;
      });
    });
  });

  describe('new ClientCompany page', () => {
    beforeEach(() => {
      cy.visit(`${clientCompanyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ClientCompany');
    });

    it('should create an instance of ClientCompany', () => {
      cy.get(`[data-cy="name"]`).type('kind pat');
      cy.get(`[data-cy="name"]`).should('have.value', 'kind pat');

      cy.get(`[data-cy="invoicePrefix"]`).type('amongst given supposing');
      cy.get(`[data-cy="invoicePrefix"]`).should('have.value', 'amongst given supposing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        clientCompany = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', clientCompanyPageUrlPattern);
    });
  });
});
