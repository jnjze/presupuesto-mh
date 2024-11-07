import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './centro-costo.reducer';

export const CentroCostoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const centroCostoEntity = useAppSelector(state => state.centroCosto.entity);
  const loading = useAppSelector(state => state.centroCosto.loading);
  const updating = useAppSelector(state => state.centroCosto.updating);
  const updateSuccess = useAppSelector(state => state.centroCosto.updateSuccess);

  const handleClose = () => {
    navigate('/centro-costo');
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
      ...centroCostoEntity,
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
          ...centroCostoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="presupuestoApp.centroCosto.home.createOrEditLabel" data-cy="CentroCostoCreateUpdateHeading">
            Crear o editar Centro Costo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="centro-costo-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Codigo" id="centro-costo-codigo" name="codigo" data-cy="codigo" type="text" />
              <ValidatedField label="Nombre" id="centro-costo-nombre" name="nombre" data-cy="nombre" type="text" />
              <ValidatedField label="Descripcion" id="centro-costo-descripcion" name="descripcion" data-cy="descripcion" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/centro-costo" replace color="info">
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

export default CentroCostoUpdate;
