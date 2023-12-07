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

describe('CCPhoneNum e2e test', () => {
  const cCPhoneNumPageUrl = '/cc-phone-num';
  const cCPhoneNumPageUrlPattern = new RegExp('/cc-phone-num(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cCPhoneNumSample = { phoneNum: 'tabernacle oddly physically' };

  let cCPhoneNum;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/cc-phone-nums+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/cc-phone-nums').as('postEntityRequest');
    cy.intercept('DELETE', '/api/cc-phone-nums/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cCPhoneNum) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cc-phone-nums/${cCPhoneNum.id}`,
      }).then(() => {
        cCPhoneNum = undefined;
      });
    }
  });

  it('CCPhoneNums menu should load CCPhoneNums page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cc-phone-num');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CCPhoneNum').should('exist');
    cy.url().should('match', cCPhoneNumPageUrlPattern);
  });

  describe('CCPhoneNum page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cCPhoneNumPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CCPhoneNum page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cc-phone-num/new$'));
        cy.getEntityCreateUpdateHeading('CCPhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCPhoneNumPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/cc-phone-nums',
          body: cCPhoneNumSample,
        }).then(({ body }) => {
          cCPhoneNum = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/cc-phone-nums+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/cc-phone-nums?page=0&size=20>; rel="last",<http://localhost/api/cc-phone-nums?page=0&size=20>; rel="first"',
              },
              body: [cCPhoneNum],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cCPhoneNumPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CCPhoneNum page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cCPhoneNum');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCPhoneNumPageUrlPattern);
      });

      it('edit button click should load edit CCPhoneNum page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCPhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCPhoneNumPageUrlPattern);
      });

      it('edit button click should load edit CCPhoneNum page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CCPhoneNum');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCPhoneNumPageUrlPattern);
      });

      it('last delete button click should delete instance of CCPhoneNum', () => {
        cy.intercept('GET', '/api/cc-phone-nums/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cCPhoneNum').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cCPhoneNumPageUrlPattern);

        cCPhoneNum = undefined;
      });
    });
  });

  describe('new CCPhoneNum page', () => {
    beforeEach(() => {
      cy.visit(`${cCPhoneNumPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CCPhoneNum');
    });

    it('should create an instance of CCPhoneNum', () => {
      cy.get(`[data-cy="phoneNum"]`).type('levy');
      cy.get(`[data-cy="phoneNum"]`).should('have.value', 'levy');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cCPhoneNum = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cCPhoneNumPageUrlPattern);
    });
  });
});
