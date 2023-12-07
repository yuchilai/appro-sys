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

describe('Approval e2e test', () => {
  const approvalPageUrl = '/approval';
  const approvalPageUrlPattern = new RegExp('/approval(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const approvalSample = { approved: true, approvalDateTime: '2023-12-07T09:44:17.982Z' };

  let approval;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/approvals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/approvals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/approvals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (approval) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/approvals/${approval.id}`,
      }).then(() => {
        approval = undefined;
      });
    }
  });

  it('Approvals menu should load Approvals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('approval');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Approval').should('exist');
    cy.url().should('match', approvalPageUrlPattern);
  });

  describe('Approval page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(approvalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Approval page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/approval/new$'));
        cy.getEntityCreateUpdateHeading('Approval');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approvalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/approvals',
          body: approvalSample,
        }).then(({ body }) => {
          approval = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/approvals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/approvals?page=0&size=20>; rel="last",<http://localhost/api/approvals?page=0&size=20>; rel="first"',
              },
              body: [approval],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(approvalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Approval page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('approval');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approvalPageUrlPattern);
      });

      it('edit button click should load edit Approval page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Approval');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approvalPageUrlPattern);
      });

      it('edit button click should load edit Approval page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Approval');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approvalPageUrlPattern);
      });

      it('last delete button click should delete instance of Approval', () => {
        cy.intercept('GET', '/api/approvals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('approval').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', approvalPageUrlPattern);

        approval = undefined;
      });
    });
  });

  describe('new Approval page', () => {
    beforeEach(() => {
      cy.visit(`${approvalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Approval');
    });

    it('should create an instance of Approval', () => {
      cy.get(`[data-cy="approved"]`).should('not.be.checked');
      cy.get(`[data-cy="approved"]`).click();
      cy.get(`[data-cy="approved"]`).should('be.checked');

      cy.get(`[data-cy="approvalDateTime"]`).type('2023-12-07T05:38');
      cy.get(`[data-cy="approvalDateTime"]`).blur();
      cy.get(`[data-cy="approvalDateTime"]`).should('have.value', '2023-12-07T05:38');

      cy.get(`[data-cy="comments"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="comments"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        approval = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', approvalPageUrlPattern);
    });
  });
});
