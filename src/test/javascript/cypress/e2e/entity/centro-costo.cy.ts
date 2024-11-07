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

describe('CentroCosto e2e test', () => {
  const centroCostoPageUrl = '/centro-costo';
  const centroCostoPageUrlPattern = new RegExp('/centro-costo(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const centroCostoSample = {};

  let centroCosto;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/centro-costos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/centro-costos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/centro-costos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (centroCosto) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/centro-costos/${centroCosto.id}`,
      }).then(() => {
        centroCosto = undefined;
      });
    }
  });

  it('CentroCostos menu should load CentroCostos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('centro-costo');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CentroCosto').should('exist');
    cy.url().should('match', centroCostoPageUrlPattern);
  });

  describe('CentroCosto page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(centroCostoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CentroCosto page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/centro-costo/new$'));
        cy.getEntityCreateUpdateHeading('CentroCosto');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', centroCostoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/centro-costos',
          body: centroCostoSample,
        }).then(({ body }) => {
          centroCosto = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/centro-costos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [centroCosto],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(centroCostoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CentroCosto page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('centroCosto');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', centroCostoPageUrlPattern);
      });

      it('edit button click should load edit CentroCosto page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CentroCosto');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', centroCostoPageUrlPattern);
      });

      it('edit button click should load edit CentroCosto page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CentroCosto');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', centroCostoPageUrlPattern);
      });

      it('last delete button click should delete instance of CentroCosto', () => {
        cy.intercept('GET', '/api/centro-costos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('centroCosto').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', centroCostoPageUrlPattern);

        centroCosto = undefined;
      });
    });
  });

  describe('new CentroCosto page', () => {
    beforeEach(() => {
      cy.visit(`${centroCostoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CentroCosto');
    });

    it('should create an instance of CentroCosto', () => {
      cy.get(`[data-cy="codigo"]`).type('despite blond entrench');
      cy.get(`[data-cy="codigo"]`).should('have.value', 'despite blond entrench');

      cy.get(`[data-cy="nombre"]`).type('pfft');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'pfft');

      cy.get(`[data-cy="descripcion"]`).type('hubris delightfully');
      cy.get(`[data-cy="descripcion"]`).should('have.value', 'hubris delightfully');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        centroCosto = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', centroCostoPageUrlPattern);
    });
  });
});
