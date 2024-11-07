import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { getEntities as getSubPlans } from 'app/entities/sub-plan/sub-plan.reducer';
import { getEntities as getUnidadFuncionals } from 'app/entities/unidad-funcional/unidad-funcional.reducer';
import { getEntities as getRubros } from 'app/entities/rubro/rubro.reducer';
import { getEntities as getCentroCostos } from 'app/entities/centro-costo/centro-costo.reducer';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { createEntity, getEntity, reset, updateEntity } from './presupuesto.reducer';

export const PresupuestoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plans = useAppSelector(state => state.plan.entities);
  const subPlans = useAppSelector(state => state.subPlan.entities);
  const unidadFuncionals = useAppSelector(state => state.unidadFuncional.entities);
  const rubros = useAppSelector(state => state.rubro.entities);
  const centroCostos = useAppSelector(state => state.centroCosto.entities);
  const presupuestoEntity = useAppSelector(state => state.presupuesto.entity);
  const loading = useAppSelector(state => state.presupuesto.loading);
  const updating = useAppSelector(state => state.presupuesto.updating);
  const updateSuccess = useAppSelector(state => state.presupuesto.updateSuccess);
  const estadoValues = Object.keys(Estado);

  const handleClose = () => {
    navigate('/presupuesto');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
    dispatch(getSubPlans({}));
    dispatch(getUnidadFuncionals({}));
    dispatch(getRubros({}));
    dispatch(getCentroCostos({}));
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
      ...presupuestoEntity,
      ...values,
      plan: plans.find(it => it.id.toString() === values.plan?.toString()),
      subPlan: subPlans.find(it => it.id.toString() === values.subPlan?.toString()),
      unidadFuncional: unidadFuncionals.find(it => it.id.toString() === values.unidadFuncional?.toString()),
      rubro: rubros.find(it => it.id.toString() === values.rubro?.toString()),
      centroCosto: centroCostos.find(it => it.id.toString() === values.centroCosto?.toString()),
      finalizada: true,
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
          estado: presupuestoEntity.estado,
          ...presupuestoEntity,
          plan: presupuestoEntity?.plan?.id,
          subPlan: presupuestoEntity?.subPlan?.id,
          unidadFuncional: presupuestoEntity?.unidadFuncional?.id,
          rubro: presupuestoEntity?.rubro?.id,
          centroCosto: presupuestoEntity?.centroCosto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="presupuestoApp.presupuesto.home.createOrEditLabel" data-cy="PresupuestoCreateUpdateHeading">
            Crear o editar Presupuesto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="presupuesto-id" label="Consecutivo" validate={{ required: true }} />
              ) : null}
              <ValidatedField id="presupuesto-plan" name="plan" data-cy="plan" label="Plan" type="select" disabled>
                <option value="" key="0" />
                {plans
                  ? plans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="presupuesto-subPlan" name="subPlan" data-cy="subPlan" label="Sub Plan" type="select" disabled>
                <option value="" key="0" />
                {subPlans
                  ? subPlans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="presupuesto-unidadFuncional"
                name="unidadFuncional"
                data-cy="unidadFuncional"
                label="Unidad Funcional"
                type="select"
              >
                <option value="" key="0" />
                {unidadFuncionals
                  ? unidadFuncionals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="presupuesto-rubro" name="rubro" data-cy="rubro" label="Rubro" type="select">
                <option value="" key="0" />
                {rubros
                  ? rubros.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="presupuesto-centroCosto" name="centroCosto" data-cy="centroCosto" label="Centro Costo" type="select">
                <option value="" key="0" />
                {centroCostos
                  ? centroCostos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Descripcion Actividad"
                id="presupuesto-descripcionActividad"
                name="descripcionActividad"
                data-cy="descripcionActividad"
                type="text"
                disabled
              />
              <ValidatedField
                label="Fecha Inicio"
                id="presupuesto-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="date"
                disabled
              />
              <ValidatedField label="Fecha Final" id="presupuesto-fechaFinal" name="fechaFinal" data-cy="fechaFinal" type="date" disabled />
              <ValidatedField
                label="Fecha Registro"
                id="presupuesto-fechaRegistro"
                name="fechaRegistro"
                data-cy="fechaRegistro"
                type="date"
                disabled
              />
              <ValidatedField
                label="Nombre Responsable"
                id="presupuesto-nombreResponsable"
                name="nombreResponsable"
                data-cy="nombreResponsable"
                type="text"
                disabled
              />
              <ValidatedField label="Estado" id="presupuesto-estado" name="estado" data-cy="estado" type="select" disabled>
                {estadoValues.map(estado => (
                  <option value={estado} key={estado}>
                    {estado === 'APROBADO' ? 'Aprobado' : ''}
                    {estado === 'RECHAZADO' ? 'Rechazado' : ''}
                    {estado === 'SIN_ASIGNAR' ? 'Sin asignar' : ''}
                    {estado === 'DEVOLUCION' ? 'Devolución' : ''}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Correo Responsable"
                id="presupuesto-correoResponsable"
                name="correoResponsable"
                data-cy="correoResponsable"
                type="text"
                disabled
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/presupuesto" replace color="info">
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

export default PresupuestoUpdate;
