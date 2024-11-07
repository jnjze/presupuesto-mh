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

describe('UnidadFuncional e2e test', () => {
  const unidadFuncionalPageUrl = '/unidad-funcional';
  const unidadFuncionalPageUrlPattern = new RegExp('/unidad-funcional(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const unidadFuncionalSample = {};

  let unidadFuncional;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/unidad-funcionals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/unidad-funcionals').as('postEntityRequest');
    cy.intercept('DELETE', '/api/unidad-funcionals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (unidadFuncional) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/unidad-funcionals/${unidadFuncional.id}`,
      }).then(() => {
        unidadFuncional = undefined;
      });
    }
  });

  it('UnidadFuncionals menu should load UnidadFuncionals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('unidad-funcional');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UnidadFuncional').should('exist');
    cy.url().should('match', unidadFuncionalPageUrlPattern);
  });

  describe('UnidadFuncional page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(unidadFuncionalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UnidadFuncional page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/unidad-funcional/new$'));
        cy.getEntityCreateUpdateHeading('UnidadFuncional');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', unidadFuncionalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/unidad-funcionals',
          body: unidadFuncionalSample,
        }).then(({ body }) => {
          unidadFuncional = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/unidad-funcionals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [unidadFuncional],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(unidadFuncionalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UnidadFuncional page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('unidadFuncional');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', unidadFuncionalPageUrlPattern);
      });

      it('edit button click should load edit UnidadFuncional page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UnidadFuncional');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', unidadFuncionalPageUrlPattern);
      });

      it('edit button click should load edit UnidadFuncional page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UnidadFuncional');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', unidadFuncionalPageUrlPattern);
      });

      it('last delete button click should delete instance of UnidadFuncional', () => {
        cy.intercept('GET', '/api/unidad-funcionals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('unidadFuncional').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', unidadFuncionalPageUrlPattern);

        unidadFuncional = undefined;
      });
    });
  });

  describe('new UnidadFuncional page', () => {
    beforeEach(() => {
      cy.visit(`${unidadFuncionalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UnidadFuncional');
    });

    it('should create an instance of UnidadFuncional', () => {
      cy.get(`[data-cy="codigo"]`).type('brook joyful');
      cy.get(`[data-cy="codigo"]`).should('have.value', 'brook joyful');

      cy.get(`[data-cy="nombre"]`).type('inasmuch mould');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'inasmuch mould');

      cy.get(`[data-cy="descripcion"]`).type('opposite store');
      cy.get(`[data-cy="descripcion"]`).should('have.value', 'opposite store');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        unidadFuncional = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', unidadFuncionalPageUrlPattern);
    });
  });
});
