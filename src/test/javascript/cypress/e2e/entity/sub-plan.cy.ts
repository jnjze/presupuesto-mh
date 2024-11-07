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

describe('SubPlan e2e test', () => {
  const subPlanPageUrl = '/sub-plan';
  const subPlanPageUrlPattern = new RegExp('/sub-plan(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const subPlanSample = {};

  let subPlan;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sub-plans+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sub-plans').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sub-plans/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (subPlan) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sub-plans/${subPlan.id}`,
      }).then(() => {
        subPlan = undefined;
      });
    }
  });

  it('SubPlans menu should load SubPlans page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sub-plan');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SubPlan').should('exist');
    cy.url().should('match', subPlanPageUrlPattern);
  });

  describe('SubPlan page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(subPlanPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SubPlan page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sub-plan/new$'));
        cy.getEntityCreateUpdateHeading('SubPlan');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subPlanPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sub-plans',
          body: subPlanSample,
        }).then(({ body }) => {
          subPlan = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sub-plans+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [subPlan],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(subPlanPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SubPlan page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('subPlan');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subPlanPageUrlPattern);
      });

      it('edit button click should load edit SubPlan page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubPlan');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subPlanPageUrlPattern);
      });

      it('edit button click should load edit SubPlan page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubPlan');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subPlanPageUrlPattern);
      });

      it('last delete button click should delete instance of SubPlan', () => {
        cy.intercept('GET', '/api/sub-plans/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('subPlan').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subPlanPageUrlPattern);

        subPlan = undefined;
      });
    });
  });

  describe('new SubPlan page', () => {
    beforeEach(() => {
      cy.visit(`${subPlanPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SubPlan');
    });

    it('should create an instance of SubPlan', () => {
      cy.get(`[data-cy="codigo"]`).type('loftily unethically');
      cy.get(`[data-cy="codigo"]`).should('have.value', 'loftily unethically');

      cy.get(`[data-cy="nombre"]`).type('meh ecstatic now');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'meh ecstatic now');

      cy.get(`[data-cy="descripcion"]`).type('blah');
      cy.get(`[data-cy="descripcion"]`).should('have.value', 'blah');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        subPlan = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', subPlanPageUrlPattern);
    });
  });
});
