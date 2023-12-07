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

describe('CIPhoneNum e2e test', () => {
  const cIPhoneNumPageUrl = '/ci-phone-num';
  const cIPhoneNumPageUrlPattern = new RegExp('/ci-phone-num(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cIPhoneNumSample = { phoneNum: 'till' };

  let cIPhoneNum;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ci-phone-nums+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ci-phone-nums').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ci-phone-nums/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cIPhoneNum) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ci-phone-nums/${cIPhoneNum.id}`,
      }).then(() => {
        cIPhoneNum = undefined;
      });
    }
  });

  it('CIPhoneNums menu should load CIPhoneNums page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ci-phone-num');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CIPhoneNum').should('exist');
    cy.url().should('match', cIPhoneNumPageUrlPattern);
  });

  describe('CIPhoneNum page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cIPhoneNumPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CIPhoneNum page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ci-phone-num/new$'));
        cy.getEntityCreateUpdateHeading('CIPhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIPhoneNumPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ci-phone-nums',
          body: cIPhoneNumSample,
        }).then(({ body }) => {
          cIPhoneNum = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ci-phone-nums+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ci-phone-nums?page=0&size=20>; rel="last",<http://localhost/api/ci-phone-nums?page=0&size=20>; rel="first"',
              },
              body: [cIPhoneNum],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cIPhoneNumPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CIPhoneNum page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cIPhoneNum');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIPhoneNumPageUrlPattern);
      });

      it('edit button click should load edit CIPhoneNum page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIPhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIPhoneNumPageUrlPattern);
      });

      it('edit button click should load edit CIPhoneNum page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CIPhoneNum');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIPhoneNumPageUrlPattern);
      });

      it('last delete button click should delete instance of CIPhoneNum', () => {
        cy.intercept('GET', '/api/ci-phone-nums/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cIPhoneNum').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cIPhoneNumPageUrlPattern);

        cIPhoneNum = undefined;
      });
    });
  });

  describe('new CIPhoneNum page', () => {
    beforeEach(() => {
      cy.visit(`${cIPhoneNumPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CIPhoneNum');
    });

    it('should create an instance of CIPhoneNum', () => {
      cy.get(`[data-cy="phoneNum"]`).type('mmm');
      cy.get(`[data-cy="phoneNum"]`).should('have.value', 'mmm');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cIPhoneNum = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cIPhoneNumPageUrlPattern);
    });
  });
});
