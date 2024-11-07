import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { createEntity, getEntity, reset, updateEntity } from './sub-plan.reducer';

export const SubPlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plan.entities);
  const subPlanEntity = useAppSelector(state => state.subPlan.entity);
  const loading = useAppSelector(state => state.subPlan.loading);
  const updating = useAppSelector(state => state.subPlan.updating);
  const updateSuccess = useAppSelector(state => state.subPlan.updateSuccess);

  const handleClose = () => {
    navigate('/sub-plan');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...subPlanEntity,
      ...values,
      plan: plans.find(it => it.id.toString() === values.plan?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...subPlanEntity,
          plan: subPlanEntity?.plan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="presupuestoApp.subPlan.home.createOrEditLabel" data-cy="SubPlanCreateUpdateHeading">
            Crear o editar Sub Plan
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sub-plan-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Codigo" id="sub-plan-codigo" name="codigo" data-cy="codigo" type="text" />
              <ValidatedField label="Nombre" id="sub-plan-nombre" name="nombre" data-cy="nombre" type="text" />
              <ValidatedField label="Descripcion" id="sub-plan-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <ValidatedField id="sub-plan-plan" name="plan" data-cy="plan" label="Plan" type="select">
                <option value="" key="0" />
                {plans
                  ? plans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sub-plan" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SubPlanUpdate;
