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

describe('PhoneNum e2e test', () => {
  const phoneNumPageUrl = '/phone-num';
  const phoneNumPageUrlPattern = new RegExp('/phone-num(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const phoneNumSample = { phoneNum: 'subsidence teeming cob' };

  let phoneNum;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/phone-nums+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/phone-nums').as('postEntityRequest');
    cy.intercept('DELETE', '/api/phone-nums/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (phoneNum) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/phone-nums/${phoneNum.id}`,
      }).then(() => {
        phoneNum = undefined;
      });
    }
  });

  it('PhoneNums menu should load PhoneNums page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('phone-num');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PhoneNum').should('exist');
    cy.url().should('match', phoneNumPageUrlPattern);
  });

  describe('PhoneNum page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(phoneNumPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PhoneNum page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/phone-num/new$'));
        cy.getEntityCreateUpdateHeading('PhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', phoneNumPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/phone-nums',
          body: phoneNumSample,
        }).then(({ body }) => {
          phoneNum = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/phone-nums+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/phone-nums?page=0&size=20>; rel="last",<http://localhost/api/phone-nums?page=0&size=20>; rel="first"',
              },
              body: [phoneNum],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(phoneNumPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PhoneNum page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('phoneNum');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', phoneNumPageUrlPattern);
      });

      it('edit button click should load edit PhoneNum page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PhoneNum');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', phoneNumPageUrlPattern);
      });

      it('edit button click should load edit PhoneNum page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PhoneNum');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', phoneNumPageUrlPattern);
      });

      it('last delete button click should delete instance of PhoneNum', () => {
        cy.intercept('GET', '/api/phone-nums/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('phoneNum').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', phoneNumPageUrlPattern);

        phoneNum = undefined;
      });
    });
  });

  describe('new PhoneNum page', () => {
    beforeEach(() => {
      cy.visit(`${phoneNumPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PhoneNum');
    });

    it('should create an instance of PhoneNum', () => {
      cy.get(`[data-cy="phoneNum"]`).type('shawl unrealistic about');
      cy.get(`[data-cy="phoneNum"]`).should('have.value', 'shawl unrealistic about');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        phoneNum = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', phoneNumPageUrlPattern);
    });
  });
});
