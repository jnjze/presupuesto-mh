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

describe('Rubro e2e test', () => {
  const rubroPageUrl = '/rubro';
  const rubroPageUrlPattern = new RegExp('/rubro(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rubroSample = {};

  let rubro;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/rubros+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/rubros').as('postEntityRequest');
    cy.intercept('DELETE', '/api/rubros/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rubro) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/rubros/${rubro.id}`,
      }).then(() => {
        rubro = undefined;
      });
    }
  });

  it('Rubros menu should load Rubros page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('rubro');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Rubro').should('exist');
    cy.url().should('match', rubroPageUrlPattern);
  });

  describe('Rubro page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rubroPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Rubro page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/rubro/new$'));
        cy.getEntityCreateUpdateHeading('Rubro');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rubroPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/rubros',
          body: rubroSample,
        }).then(({ body }) => {
          rubro = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/rubros+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [rubro],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rubroPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Rubro page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rubro');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rubroPageUrlPattern);
      });

      it('edit button click should load edit Rubro page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rubro');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rubroPageUrlPattern);
      });

      it('edit button click should load edit Rubro page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rubro');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rubroPageUrlPattern);
      });

      it('last delete button click should delete instance of Rubro', () => {
        cy.intercept('GET', '/api/rubros/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('rubro').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rubroPageUrlPattern);

        rubro = undefined;
      });
    });
  });

  describe('new Rubro page', () => {
    beforeEach(() => {
      cy.visit(`${rubroPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Rubro');
    });

    it('should create an instance of Rubro', () => {
      cy.get(`[data-cy="codigo"]`).type('instead');
      cy.get(`[data-cy="codigo"]`).should('have.value', 'instead');

      cy.get(`[data-cy="nombre"]`).type('ample');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'ample');

      cy.get(`[data-cy="descripcion"]`).type('coolly spew');
      cy.get(`[data-cy="descripcion"]`).should('have.value', 'coolly spew');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rubro = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rubroPageUrlPattern);
    });
  });
});
