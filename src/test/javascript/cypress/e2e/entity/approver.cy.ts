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

describe('Approver e2e test', () => {
  const approverPageUrl = '/approver';
  const approverPageUrlPattern = new RegExp('/approver(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const approverSample = { assignedDate: '2023-12-06T19:11:21.934Z' };

  let approver;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/approvers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/approvers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/approvers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (approver) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/approvers/${approver.id}`,
      }).then(() => {
        approver = undefined;
      });
    }
  });

  it('Approvers menu should load Approvers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('approver');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Approver').should('exist');
    cy.url().should('match', approverPageUrlPattern);
  });

  describe('Approver page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(approverPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Approver page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/approver/new$'));
        cy.getEntityCreateUpdateHeading('Approver');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approverPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/approvers',
          body: approverSample,
        }).then(({ body }) => {
          approver = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/approvers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/approvers?page=0&size=20>; rel="last",<http://localhost/api/approvers?page=0&size=20>; rel="first"',
              },
              body: [approver],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(approverPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Approver page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('approver');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approverPageUrlPattern);
      });

      it('edit button click should load edit Approver page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Approver');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approverPageUrlPattern);
      });

      it('edit button click should load edit Approver page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Approver');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approverPageUrlPattern);
      });

      it('last delete button click should delete instance of Approver', () => {
        cy.intercept('GET', '/api/approvers/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('approver').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approverPageUrlPattern);

        approver = undefined;
      });
    });
  });

  describe('new Approver page', () => {
    beforeEach(() => {
      cy.visit(`${approverPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Approver');
    });

    it('should create an instance of Approver', () => {
      cy.setFieldImageAsBytesOfEntity('signature', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="assignedDate"]`).type('2023-12-06T18:02');
      cy.get(`[data-cy="assignedDate"]`).blur();
      cy.get(`[data-cy="assignedDate"]`).should('have.value', '2023-12-06T18:02');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        approver = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', approverPageUrlPattern);
    });
  });
});
