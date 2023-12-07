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

describe('InvoiceBillingInfo e2e test', () => {
  const invoiceBillingInfoPageUrl = '/invoice-billing-info';
  const invoiceBillingInfoPageUrlPattern = new RegExp('/invoice-billing-info(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const invoiceBillingInfoSample = {
    firstName: 'Wilbert',
    lastname: 'unlucky',
    phoneNum: 'during yet real',
    address1: 'obnoxiously oddball lathe',
    city: 'Leesburg',
    state: 'safe pearl duel',
    zipCode: '34947',
  };

  let invoiceBillingInfo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/invoice-billing-infos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/invoice-billing-infos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/invoice-billing-infos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (invoiceBillingInfo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/invoice-billing-infos/${invoiceBillingInfo.id}`,
      }).then(() => {
        invoiceBillingInfo = undefined;
      });
    }
  });

  it('InvoiceBillingInfos menu should load InvoiceBillingInfos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('invoice-billing-info');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InvoiceBillingInfo').should('exist');
    cy.url().should('match', invoiceBillingInfoPageUrlPattern);
  });

  describe('InvoiceBillingInfo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(invoiceBillingInfoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InvoiceBillingInfo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/invoice-billing-info/new$'));
        cy.getEntityCreateUpdateHeading('InvoiceBillingInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', invoiceBillingInfoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/invoice-billing-infos',
          body: invoiceBillingInfoSample,
        }).then(({ body }) => {
          invoiceBillingInfo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/invoice-billing-infos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/invoice-billing-infos?page=0&size=20>; rel="last",<http://localhost/api/invoice-billing-infos?page=0&size=20>; rel="first"',
              },
              body: [invoiceBillingInfo],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(invoiceBillingInfoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InvoiceBillingInfo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('invoiceBillingInfo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', invoiceBillingInfoPageUrlPattern);
      });

      it('edit button click should load edit InvoiceBillingInfo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InvoiceBillingInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', invoiceBillingInfoPageUrlPattern);
      });

      it('edit button click should load edit InvoiceBillingInfo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InvoiceBillingInfo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', invoiceBillingInfoPageUrlPattern);
      });

      it('last delete button click should delete instance of InvoiceBillingInfo', () => {
        cy.intercept('GET', '/api/invoice-billing-infos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('invoiceBillingInfo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', invoiceBillingInfoPageUrlPattern);

        invoiceBillingInfo = undefined;
      });
    });
  });

  describe('new InvoiceBillingInfo page', () => {
    beforeEach(() => {
      cy.visit(`${invoiceBillingInfoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InvoiceBillingInfo');
    });

    it('should create an instance of InvoiceBillingInfo', () => {
      cy.get(`[data-cy="firstName"]`).type('Royce');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Royce');

      cy.get(`[data-cy="lastname"]`).type('considering unaccountably or');
      cy.get(`[data-cy="lastname"]`).should('have.value', 'considering unaccountably or');

      cy.get(`[data-cy="email"]`).type('#]4:0Z@Dh:3R.~x=cR}');
      cy.get(`[data-cy="email"]`).should('have.value', '#]4:0Z@Dh:3R.~x=cR}');

      cy.get(`[data-cy="phoneNum"]`).type('toward whose');
      cy.get(`[data-cy="phoneNum"]`).should('have.value', 'toward whose');

      cy.get(`[data-cy="address1"]`).type('please yin');
      cy.get(`[data-cy="address1"]`).should('have.value', 'please yin');

      cy.get(`[data-cy="address2"]`).type('lag');
      cy.get(`[data-cy="address2"]`).should('have.value', 'lag');

      cy.get(`[data-cy="city"]`).type('Theodoreborough');
      cy.get(`[data-cy="city"]`).should('have.value', 'Theodoreborough');

      cy.get(`[data-cy="state"]`).type('wherever');
      cy.get(`[data-cy="state"]`).should('have.value', 'wherever');

      cy.get(`[data-cy="county"]`).type('ambitious voice');
      cy.get(`[data-cy="county"]`).should('have.value', 'ambitious voice');

      cy.get(`[data-cy="zipCode"]`).type('56496');
      cy.get(`[data-cy="zipCode"]`).should('have.value', '56496');

      cy.get(`[data-cy="country"]`).type('Algeria');
      cy.get(`[data-cy="country"]`).should('have.value', 'Algeria');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        invoiceBillingInfo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', invoiceBillingInfoPageUrlPattern);
    });
  });
});
