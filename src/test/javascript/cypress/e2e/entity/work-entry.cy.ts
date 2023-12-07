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

describe('WorkEntry e2e test', () => {
  const workEntryPageUrl = '/work-entry';
  const workEntryPageUrlPattern = new RegExp('/work-entry(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const workEntrySample = {
    title: 'meanwhile at atop',
    date: '2023-12-07',
    description: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    status: 'SUBMITTED',
  };

  let workEntry;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/work-entries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/work-entries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/work-entries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (workEntry) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/work-entries/${workEntry.id}`,
      }).then(() => {
        workEntry = undefined;
      });
    }
  });

  it('WorkEntries menu should load WorkEntries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('work-entry');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('WorkEntry').should('exist');
    cy.url().should('match', workEntryPageUrlPattern);
  });

  describe('WorkEntry page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(workEntryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create WorkEntry page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/work-entry/new$'));
        cy.getEntityCreateUpdateHeading('WorkEntry');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', workEntryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/work-entries',
          body: workEntrySample,
        }).then(({ body }) => {
          workEntry = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/work-entries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/work-entries?page=0&size=20>; rel="last",<http://localhost/api/work-entries?page=0&size=20>; rel="first"',
              },
              body: [workEntry],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(workEntryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details WorkEntry page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('workEntry');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', workEntryPageUrlPattern);
      });

      it('edit button click should load edit WorkEntry page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WorkEntry');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', workEntryPageUrlPattern);
      });

      it('edit button click should load edit WorkEntry page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WorkEntry');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', workEntryPageUrlPattern);
      });

      it('last delete button click should delete instance of WorkEntry', () => {
        cy.intercept('GET', '/api/work-entries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('workEntry').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', workEntryPageUrlPattern);

        workEntry = undefined;
      });
    });
  });

  describe('new WorkEntry page', () => {
    beforeEach(() => {
      cy.visit(`${workEntryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('WorkEntry');
    });

    it('should create an instance of WorkEntry', () => {
      cy.get(`[data-cy="title"]`).type('virus');
      cy.get(`[data-cy="title"]`).should('have.value', 'virus');

      cy.get(`[data-cy="date"]`).type('2023-12-07');
      cy.get(`[data-cy="date"]`).blur();
      cy.get(`[data-cy="date"]`).should('have.value', '2023-12-07');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="startTime"]`).type('2023-12-07T09:00');
      cy.get(`[data-cy="startTime"]`).blur();
      cy.get(`[data-cy="startTime"]`).should('have.value', '2023-12-07T09:00');

      cy.get(`[data-cy="endTime"]`).type('2023-12-06T18:49');
      cy.get(`[data-cy="endTime"]`).blur();
      cy.get(`[data-cy="endTime"]`).should('have.value', '2023-12-06T18:49');

      cy.get(`[data-cy="hours"]`).type('13368');
      cy.get(`[data-cy="hours"]`).should('have.value', '13368');

      cy.get(`[data-cy="status"]`).select('SUBMITTED');

      cy.get(`[data-cy="totalAmount"]`).type('364.09');
      cy.get(`[data-cy="totalAmount"]`).should('have.value', '364.09');

      cy.get(`[data-cy="comment"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="comment"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.setFieldImageAsBytesOfEntity('attachments', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="fileName"]`).type('annual');
      cy.get(`[data-cy="fileName"]`).should('have.value', 'annual');

      cy.get(`[data-cy="fileType"]`).type('wholly');
      cy.get(`[data-cy="fileType"]`).should('have.value', 'wholly');

      cy.get(`[data-cy="fileSize"]`).type('7934');
      cy.get(`[data-cy="fileSize"]`).should('have.value', '7934');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        workEntry = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', workEntryPageUrlPattern);
    });
  });
});
