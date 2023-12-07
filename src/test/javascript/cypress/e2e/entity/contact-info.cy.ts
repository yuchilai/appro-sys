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

describe('ContactInfo e2e test', () => {
  const contactInfoPageUrl = '/contact-info';
  const contactInfoPageUrlPattern = new RegExp('/contact-info(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contactInfoSample = { positsion: 'implication physical', lastName: 'Dach' };

  let contactInfo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/contact-infos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/contact-infos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/contact-infos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contactInfo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/contact-infos/${contactInfo.id}`,
      }).then(() => {
        contactInfo = undefined;
      });
    }
  });

  it('ContactInfos menu should load ContactInfos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('contact-info');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ContactInfo').should('exist');
    cy.url().should('match', contactInfoPageUrlPattern);
  });

  describe('ContactInfo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contactInfoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ContactInfo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/contact-info/new$'));
        cy.getEntityCreateUpdateHeading('ContactInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactInfoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/contact-infos',
          body: contactInfoSample,
        }).then(({ body }) => {
          contactInfo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/contact-infos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/contact-infos?page=0&size=20>; rel="last",<http://localhost/api/contact-infos?page=0&size=20>; rel="first"',
              },
              body: [contactInfo],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(contactInfoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ContactInfo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contactInfo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactInfoPageUrlPattern);
      });

      it('edit button click should load edit ContactInfo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContactInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactInfoPageUrlPattern);
      });

      it('edit button click should load edit ContactInfo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContactInfo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactInfoPageUrlPattern);
      });

      it('last delete button click should delete instance of ContactInfo', () => {
        cy.intercept('GET', '/api/contact-infos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('contactInfo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactInfoPageUrlPattern);

        contactInfo = undefined;
      });
    });
  });

  describe('new ContactInfo page', () => {
    beforeEach(() => {
      cy.visit(`${contactInfoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ContactInfo');
    });

    it('should create an instance of ContactInfo', () => {
      cy.get(`[data-cy="positsion"]`).type('warmth sedately');
      cy.get(`[data-cy="positsion"]`).should('have.value', 'warmth sedately');

      cy.get(`[data-cy="firstName"]`).type('Rubye');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Rubye');

      cy.get(`[data-cy="lastName"]`).type('Reichel');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Reichel');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        contactInfo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', contactInfoPageUrlPattern);
    });
  });
});
