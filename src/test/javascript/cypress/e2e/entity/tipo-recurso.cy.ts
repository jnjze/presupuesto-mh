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

describe('TipoRecurso e2e test', () => {
  const tipoRecursoPageUrl = '/tipo-recurso';
  const tipoRecursoPageUrlPattern = new RegExp('/tipo-recurso(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const tipoRecursoSample = {};

  let tipoRecurso;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tipo-recursos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tipo-recursos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tipo-recursos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tipoRecurso) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tipo-recursos/${tipoRecurso.id}`,
      }).then(() => {
        tipoRecurso = undefined;
      });
    }
  });

  it('TipoRecursos menu should load TipoRecursos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tipo-recurso');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TipoRecurso').should('exist');
    cy.url().should('match', tipoRecursoPageUrlPattern);
  });

  describe('TipoRecurso page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tipoRecursoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TipoRecurso page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tipo-recurso/new$'));
        cy.getEntityCreateUpdateHeading('TipoRecurso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoRecursoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tipo-recursos',
          body: tipoRecursoSample,
        }).then(({ body }) => {
          tipoRecurso = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tipo-recursos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [tipoRecurso],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(tipoRecursoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TipoRecurso page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tipoRecurso');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoRecursoPageUrlPattern);
      });

      it('edit button click should load edit TipoRecurso page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TipoRecurso');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoRecursoPageUrlPattern);
      });

      it('edit button click should load edit TipoRecurso page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TipoRecurso');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoRecursoPageUrlPattern);
      });

      it('last delete button click should delete instance of TipoRecurso', () => {
        cy.intercept('GET', '/api/tipo-recursos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('tipoRecurso').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', tipoRecursoPageUrlPattern);

        tipoRecurso = undefined;
      });
    });
  });

  describe('new TipoRecurso page', () => {
    beforeEach(() => {
      cy.visit(`${tipoRecursoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TipoRecurso');
    });

    it('should create an instance of TipoRecurso', () => {
      cy.get(`[data-cy="codigo"]`).type('in hollow accurate');
      cy.get(`[data-cy="codigo"]`).should('have.value', 'in hollow accurate');

      cy.get(`[data-cy="nombre"]`).type('shrill lifestyle seemingly');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'shrill lifestyle seemingly');

      cy.get(`[data-cy="descripcion"]`).type('bah SUV');
      cy.get(`[data-cy="descripcion"]`).should('have.value', 'bah SUV');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        tipoRecurso = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', tipoRecursoPageUrlPattern);
    });
  });
});
