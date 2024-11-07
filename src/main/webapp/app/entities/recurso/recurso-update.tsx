import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTipoRecursos } from 'app/entities/tipo-recurso/tipo-recurso.reducer';
import { getEntities as getPresupuestos } from 'app/entities/presupuesto/presupuesto.reducer';
import { createEntity, getEntity, reset, updateEntity } from './recurso.reducer';

export const RecursoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoRecursos = useAppSelector(state => state.tipoRecurso.entities);
  const presupuestos = useAppSelector(state => state.presupuesto.entities);
  const recursoEntity = useAppSelector(state => state.recurso.entity);
  const loading = useAppSelector(state => state.recurso.loading);
  const updating = useAppSelector(state => state.recurso.updating);
  const updateSuccess = useAppSelector(state => state.recurso.updateSuccess);

  const handleClose = () => {
    navigate('/recurso');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTipoRecursos({}));
    dispatch(getPresupuestos({}));
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
    if (values.valor !== undefined && typeof values.valor !== 'number') {
      values.valor = Number(values.valor);
    }

    const entity = {
      ...recursoEntity,
      ...values,
      tipoRecurso: tipoRecursos.find(it => it.id.toString() === values.tipoRecurso?.toString()),
      presupuesto: presupuestos.find(it => it.id.toString() === values.presupuesto?.toString()),
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
          ...recursoEntity,
          tipoRecurso: recursoEntity?.tipoRecurso?.id,
          presupuesto: recursoEntity?.presupuesto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="presupuestoApp.recurso.home.createOrEditLabel" data-cy="RecursoCreateUpdateHeading">
            Crear o editar Recurso
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="recurso-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Mes" id="recurso-mes" name="mes" data-cy="mes" type="text" />
              <ValidatedField label="Valor" id="recurso-valor" name="valor" data-cy="valor" type="text" />
              <ValidatedField label="Observacion" id="recurso-observacion" name="observacion" data-cy="observacion" type="text" />
              <ValidatedField id="recurso-tipoRecurso" name="tipoRecurso" data-cy="tipoRecurso" label="Tipo Recurso" type="select">
                <option value="" key="0" />
                {tipoRecursos
                  ? tipoRecursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="recurso-presupuesto" name="presupuesto" data-cy="presupuesto" label="Presupuesto" type="select">
                <option value="" key="0" />
                {presupuestos
                  ? presupuestos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/recurso" replace color="info">
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

export default RecursoUpdate;
