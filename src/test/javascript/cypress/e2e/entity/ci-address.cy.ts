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

describe('CIAddress e2e test', () => {
  const cIAddressPageUrl = '/ci-address';
  const cIAddressPageUrlPattern = new RegExp('/ci-address(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cIAddressSample = { address1: 'consequently', city: 'Brooketon', state: 'research duck virtuous', zipCode: '57057-3703' };

  let cIAddress;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ci-addresses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ci-addresses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ci-addresses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cIAddress) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ci-addresses/${cIAddress.id}`,
      }).then(() => {
        cIAddress = undefined;
      });
    }
  });

  it('CIAddresses menu should load CIAddresses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ci-address');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CIAddress').should('exist');
    cy.url().should('match', cIAddressPageUrlPattern);
  });

  describe('CIAddress page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cIAddressPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CIAddress page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ci-address/new$'));
        cy.getEntityCreateUpdateHeading('CIAddress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIAddressPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ci-addresses',
          body: cIAddressSample,
        }).then(({ body }) => {
          cIAddress = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ci-addresses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ci-addresses?page=0&size=20>; rel="last",<http://localhost/api/ci-addresses?page=0&size=20>; rel="first"',
              },
              body: [cIAddress],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cIAddressPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CIAddress page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cIAddress');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIAddressPageUrlPattern);
      });

      it('edit button click should load edit CIAddress page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIAddress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIAddressPageUrlPattern);
      });

      it('edit button click should load edit CIAddress page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIAddress');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIAddressPageUrlPattern);
      });

      it('last delete button click should delete instance of CIAddress', () => {
        cy.intercept('GET', '/api/ci-addresses/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cIAddress').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIAddressPageUrlPattern);

        cIAddress = undefined;
      });
    });
  });

  describe('new CIAddress page', () => {
    beforeEach(() => {
      cy.visit(`${cIAddressPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CIAddress');
    });

    it('should create an instance of CIAddress', () => {
      cy.get(`[data-cy="address1"]`).type('but');
      cy.get(`[data-cy="address1"]`).should('have.value', 'but');

      cy.get(`[data-cy="address2"]`).type('gently upward');
      cy.get(`[data-cy="address2"]`).should('have.value', 'gently upward');

      cy.get(`[data-cy="city"]`).type('Fritschchester');
      cy.get(`[data-cy="city"]`).should('have.value', 'Fritschchester');

      cy.get(`[data-cy="state"]`).type('against');
      cy.get(`[data-cy="state"]`).should('have.value', 'against');

      cy.get(`[data-cy="county"]`).type('zinc');
      cy.get(`[data-cy="county"]`).should('have.value', 'zinc');

      cy.get(`[data-cy="zipCode"]`).type('30653-1295');
      cy.get(`[data-cy="zipCode"]`).should('have.value', '30653-1295');

      cy.get(`[data-cy="country"]`).type('Luxembourg');
      cy.get(`[data-cy="country"]`).should('have.value', 'Luxembourg');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cIAddress = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cIAddressPageUrlPattern);
    });
  });
});
