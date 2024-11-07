import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './unidad-funcional.reducer';

export const UnidadFuncionalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const unidadFuncionalEntity = useAppSelector(state => state.unidadFuncional.entity);
  const loading = useAppSelector(state => state.unidadFuncional.loading);
  const updating = useAppSelector(state => state.unidadFuncional.updating);
  const updateSuccess = useAppSelector(state => state.unidadFuncional.updateSuccess);

  const handleClose = () => {
    navigate('/unidad-funcional');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...unidadFuncionalEntity,
      ...values,
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
          ...unidadFuncionalEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="presupuestoApp.unidadFuncional.home.createOrEditLabel" data-cy="UnidadFuncionalCreateUpdateHeading">
            Crear o editar Unidad Funcional
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
                <ValidatedField name="id" required readOnly id="unidad-funcional-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Codigo" id="unidad-funcional-codigo" name="codigo" data-cy="codigo" type="text" />
              <ValidatedField label="Nombre" id="unidad-funcional-nombre" name="nombre" data-cy="nombre" type="text" />
              <ValidatedField label="Descripcion" id="unidad-funcional-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/unidad-funcional" replace color="info">
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

export default UnidadFuncionalUpdate;
