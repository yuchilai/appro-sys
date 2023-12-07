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

describe('AccountsPayable e2e test', () => {
  const accountsPayablePageUrl = '/accounts-payable';
  const accountsPayablePageUrlPattern = new RegExp('/accounts-payable(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const accountsPayableSample = {
    deptName: 'brr morbidity athwart',
    repLastName: 'plus',
    repEmail: 'energetically once outlook',
    repPhoneNum: 'inside',
    isUsedForInvoice: false,
  };

  let accountsPayable;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/accounts-payables+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/accounts-payables').as('postEntityRequest');
    cy.intercept('DELETE', '/api/accounts-payables/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (accountsPayable) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/accounts-payables/${accountsPayable.id}`,
      }).then(() => {
        accountsPayable = undefined;
      });
    }
  });

  it('AccountsPayables menu should load AccountsPayables page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('accounts-payable');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AccountsPayable').should('exist');
    cy.url().should('match', accountsPayablePageUrlPattern);
  });

  describe('AccountsPayable page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(accountsPayablePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AccountsPayable page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/accounts-payable/new$'));
        cy.getEntityCreateUpdateHeading('AccountsPayable');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPayablePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/accounts-payables',
          body: accountsPayableSample,
        }).then(({ body }) => {
          accountsPayable = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/accounts-payables+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/accounts-payables?page=0&size=20>; rel="last",<http://localhost/api/accounts-payables?page=0&size=20>; rel="first"',
              },
              body: [accountsPayable],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(accountsPayablePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AccountsPayable page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('accountsPayable');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPayablePageUrlPattern);
      });

      it('edit button click should load edit AccountsPayable page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AccountsPayable');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPayablePageUrlPattern);
      });

      it('edit button click should load edit AccountsPayable page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AccountsPayable');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPayablePageUrlPattern);
      });

      it('last delete button click should delete instance of AccountsPayable', () => {
        cy.intercept('GET', '/api/accounts-payables/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('accountsPayable').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', accountsPayablePageUrlPattern);

        accountsPayable = undefined;
      });
    });
  });

  describe('new AccountsPayable page', () => {
    beforeEach(() => {
      cy.visit(`${accountsPayablePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AccountsPayable');
    });

    it('should create an instance of AccountsPayable', () => {
      cy.get(`[data-cy="deptName"]`).type('after circle potentially');
      cy.get(`[data-cy="deptName"]`).should('have.value', 'after circle potentially');

      cy.get(`[data-cy="repLastName"]`).type('boo unexpectedly groom');
      cy.get(`[data-cy="repLastName"]`).should('have.value', 'boo unexpectedly groom');

      cy.get(`[data-cy="repFirstName"]`).type('scornful wannabe');
      cy.get(`[data-cy="repFirstName"]`).should('have.value', 'scornful wannabe');

      cy.get(`[data-cy="repEmail"]`).type('er debilitate');
      cy.get(`[data-cy="repEmail"]`).should('have.value', 'er debilitate');

      cy.get(`[data-cy="repPhoneNum"]`).type('limb meanwhile');
      cy.get(`[data-cy="repPhoneNum"]`).should('have.value', 'limb meanwhile');

      cy.get(`[data-cy="isUsedForInvoice"]`).should('not.be.checked');
      cy.get(`[data-cy="isUsedForInvoice"]`).click();
      cy.get(`[data-cy="isUsedForInvoice"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        accountsPayable = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', accountsPayablePageUrlPattern);
    });
  });
});
