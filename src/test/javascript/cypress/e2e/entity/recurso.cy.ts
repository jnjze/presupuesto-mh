import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Recurso e2e test', () => {
  const recursoPageUrl = '/recurso';
  const recursoPageUrlPattern = new RegExp('/recurso(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const recursoSample = {};

  let recurso;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/recursos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/recursos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/recursos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (recurso) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/recursos/${recurso.id}`,
      }).then(() => {
        recurso = undefined;
      });
    }
  });

  it('Recursos menu should load Recursos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('recurso');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Recurso').should('exist');
    cy.url().should('match', recursoPageUrlPattern);
  });

  describe('Recurso page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(recursoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Recurso page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/recurso/new$'));
        cy.getEntityCreateUpdateHeading('Recurso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', recursoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/recursos',
          body: recursoSample,
        }).then(({ body }) => {
          recurso = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/recursos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [recurso],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(recursoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Recurso page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('recurso');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', recursoPageUrlPattern);
      });

      it('edit button click should load edit Recurso page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Recurso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', recursoPageUrlPattern);
      });

      it('edit button click should load edit Recurso page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Recurso');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', recursoPageUrlPattern);
      });

      it('last delete button click should delete instance of Recurso', () => {
        cy.intercept('GET', '/api/recursos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('recurso').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', recursoPageUrlPattern);

        recurso = undefined;
      });
    });
  });

  describe('new Recurso page', () => {
    beforeEach(() => {
      cy.visit(`${recursoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Recurso');
    });

    it('should create an instance of Recurso', () => {
      cy.get(`[data-cy="mes"]`).type('circa faraway');
      cy.get(`[data-cy="mes"]`).should('have.value', 'circa faraway');

      cy.get(`[data-cy="valor"]`).type('10676.31');
      cy.get(`[data-cy="valor"]`).should('have.value', '10676.31');

      cy.get(`[data-cy="observacion"]`).type('supposing');
      cy.get(`[data-cy="observacion"]`).should('have.value', 'supposing');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        recurso = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', recursoPageUrlPattern);
    });
  });
});
