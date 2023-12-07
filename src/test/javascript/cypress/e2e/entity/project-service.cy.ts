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

describe('ProjectService e2e test', () => {
  const projectServicePageUrl = '/project-service';
  const projectServicePageUrlPattern = new RegExp('/project-service(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const projectServiceSample = { name: 'safely near', fee: 23598.27 };

  let projectService;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/project-services+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/project-services').as('postEntityRequest');
    cy.intercept('DELETE', '/api/project-services/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (projectService) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/project-services/${projectService.id}`,
      }).then(() => {
        projectService = undefined;
      });
    }
  });

  it('ProjectServices menu should load ProjectServices page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('project-service');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ProjectService').should('exist');
    cy.url().should('match', projectServicePageUrlPattern);
  });

  describe('ProjectService page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(projectServicePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ProjectService page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/project-service/new$'));
        cy.getEntityCreateUpdateHeading('ProjectService');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', projectServicePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/project-services',
          body: projectServiceSample,
        }).then(({ body }) => {
          projectService = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/project-services+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/project-services?page=0&size=20>; rel="last",<http://localhost/api/project-services?page=0&size=20>; rel="first"',
              },
              body: [projectService],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(projectServicePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ProjectService page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('projectService');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', projectServicePageUrlPattern);
      });

      it('edit button click should load edit ProjectService page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProjectService');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', projectServicePageUrlPattern);
      });

      it('edit button click should load edit ProjectService page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProjectService');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', projectServicePageUrlPattern);
      });

      it('last delete button click should delete instance of ProjectService', () => {
        cy.intercept('GET', '/api/project-services/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('projectService').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', projectServicePageUrlPattern);

        projectService = undefined;
      });
    });
  });

  describe('new ProjectService page', () => {
    beforeEach(() => {
      cy.visit(`${projectServicePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ProjectService');
    });

    it('should create an instance of ProjectService', () => {
      cy.get(`[data-cy="name"]`).type('than for although');
      cy.get(`[data-cy="name"]`).should('have.value', 'than for although');

      cy.get(`[data-cy="fee"]`).type('29502.82');
      cy.get(`[data-cy="fee"]`).should('have.value', '29502.82');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="dayLength"]`).type('18756');
      cy.get(`[data-cy="dayLength"]`).should('have.value', '18756');

      cy.get(`[data-cy="extra"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="extra"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        projectService = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', projectServicePageUrlPattern);
    });
  });
});
