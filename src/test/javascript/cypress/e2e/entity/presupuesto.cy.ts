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

describe('Presupuesto e2e test', () => {
  const presupuestoPageUrl = '/presupuesto';
  const presupuestoPageUrlPattern = new RegExp('/presupuesto(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const presupuestoSample = {};

  let presupuesto;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/presupuestos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/presupuestos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/presupuestos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (presupuesto) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/presupuestos/${presupuesto.id}`,
      }).then(() => {
        presupuesto = undefined;
      });
    }
  });

  it('Presupuestos menu should load Presupuestos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('presupuesto');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Presupuesto').should('exist');
    cy.url().should('match', presupuestoPageUrlPattern);
  });

  describe('Presupuesto page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(presupuestoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Presupuesto page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/presupuesto/new$'));
        cy.getEntityCreateUpdateHeading('Presupuesto');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', presupuestoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/presupuestos',
          body: presupuestoSample,
        }).then(({ body }) => {
          presupuesto = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/presupuestos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [presupuesto],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(presupuestoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Presupuesto page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('presupuesto');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', presupuestoPageUrlPattern);
      });

      it('edit button click should load edit Presupuesto page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Presupuesto');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', presupuestoPageUrlPattern);
      });

      it('edit button click should load edit Presupuesto page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Presupuesto');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', presupuestoPageUrlPattern);
      });

      it('last delete button click should delete instance of Presupuesto', () => {
        cy.intercept('GET', '/api/presupuestos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('presupuesto').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', presupuestoPageUrlPattern);

        presupuesto = undefined;
      });
    });
  });

  describe('new Presupuesto page', () => {
    beforeEach(() => {
      cy.visit(`${presupuestoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Presupuesto');
    });

    it('should create an instance of Presupuesto', () => {
      cy.get(`[data-cy="consecutivo"]`).type('32761');
      cy.get(`[data-cy="consecutivo"]`).should('have.value', '32761');

      cy.get(`[data-cy="descripcionActividad"]`).type('usefully homely');
      cy.get(`[data-cy="descripcionActividad"]`).should('have.value', 'usefully homely');

      cy.get(`[data-cy="fechaInicio"]`).type('2024-11-01');
      cy.get(`[data-cy="fechaInicio"]`).blur();
      cy.get(`[data-cy="fechaInicio"]`).should('have.value', '2024-11-01');

      cy.get(`[data-cy="fechaFinal"]`).type('2024-10-31');
      cy.get(`[data-cy="fechaFinal"]`).blur();
      cy.get(`[data-cy="fechaFinal"]`).should('have.value', '2024-10-31');

      cy.get(`[data-cy="fechaRegistro"]`).type('2024-11-01');
      cy.get(`[data-cy="fechaRegistro"]`).blur();
      cy.get(`[data-cy="fechaRegistro"]`).should('have.value', '2024-11-01');

      cy.get(`[data-cy="nombreResponsable"]`).type('boo optimal');
      cy.get(`[data-cy="nombreResponsable"]`).should('have.value', 'boo optimal');

      cy.get(`[data-cy="estado"]`).select('RECHAZADO');

      cy.get(`[data-cy="correoResponsable"]`).type('edge');
      cy.get(`[data-cy="correoResponsable"]`).should('have.value', 'edge');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        presupuesto = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', presupuestoPageUrlPattern);
    });
  });
});
