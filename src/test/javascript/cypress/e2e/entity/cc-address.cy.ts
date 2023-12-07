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

describe('CCAddress e2e test', () => {
  const cCAddressPageUrl = '/cc-address';
  const cCAddressPageUrlPattern = new RegExp('/cc-address(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cCAddressSample = {
    address1: 'out who designer',
    city: 'Josephboro',
    state: 'blah near although',
    zipCode: '26015-6699',
    isUsedForInvoice: false,
  };

  let cCAddress;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/cc-addresses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/cc-addresses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/cc-addresses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cCAddress) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cc-addresses/${cCAddress.id}`,
      }).then(() => {
        cCAddress = undefined;
      });
    }
  });

  it('CCAddresses menu should load CCAddresses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cc-address');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CCAddress').should('exist');
    cy.url().should('match', cCAddressPageUrlPattern);
  });

  describe('CCAddress page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cCAddressPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CCAddress page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cc-address/new$'));
        cy.getEntityCreateUpdateHeading('CCAddress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCAddressPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/cc-addresses',
          body: cCAddressSample,
        }).then(({ body }) => {
          cCAddress = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/cc-addresses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/cc-addresses?page=0&size=20>; rel="last",<http://localhost/api/cc-addresses?page=0&size=20>; rel="first"',
              },
              body: [cCAddress],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cCAddressPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CCAddress page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cCAddress');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCAddressPageUrlPattern);
      });

      it('edit button click should load edit CCAddress page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCAddress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCAddressPageUrlPattern);
      });

      it('edit button click should load edit CCAddress page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCAddress');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCAddressPageUrlPattern);
      });

      it('last delete button click should delete instance of CCAddress', () => {
        cy.intercept('GET', '/api/cc-addresses/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cCAddress').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCAddressPageUrlPattern);

        cCAddress = undefined;
      });
    });
  });

  describe('new CCAddress page', () => {
    beforeEach(() => {
      cy.visit(`${cCAddressPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CCAddress');
    });

    it('should create an instance of CCAddress', () => {
      cy.get(`[data-cy="address1"]`).type('playfully slider');
      cy.get(`[data-cy="address1"]`).should('have.value', 'playfully slider');

      cy.get(`[data-cy="address2"]`).type('yippee');
      cy.get(`[data-cy="address2"]`).should('have.value', 'yippee');

      cy.get(`[data-cy="city"]`).type('Shreveport');
      cy.get(`[data-cy="city"]`).should('have.value', 'Shreveport');

      cy.get(`[data-cy="state"]`).type('uh-huh kindly');
      cy.get(`[data-cy="state"]`).should('have.value', 'uh-huh kindly');

      cy.get(`[data-cy="county"]`).type('detract');
      cy.get(`[data-cy="county"]`).should('have.value', 'detract');

      cy.get(`[data-cy="zipCode"]`).type('98242-2815');
      cy.get(`[data-cy="zipCode"]`).should('have.value', '98242-2815');

      cy.get(`[data-cy="country"]`).type('Malaysia');
      cy.get(`[data-cy="country"]`).should('have.value', 'Malaysia');

      cy.get(`[data-cy="isUsedForInvoice"]`).should('not.be.checked');
      cy.get(`[data-cy="isUsedForInvoice"]`).click();
      cy.get(`[data-cy="isUsedForInvoice"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cCAddress = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cCAddressPageUrlPattern);
    });
  });
});
