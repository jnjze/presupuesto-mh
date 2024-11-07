import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './unidad-funcional.reducer';

export const UnidadFuncionalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const unidadFuncionalEntity = useAppSelector(state => state.unidadFuncional.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unidadFuncionalDetailsHeading">Unidad Funcional</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{unidadFuncionalEntity.id}</dd>
          <dt>
            <span id="codigo">Codigo</span>
          </dt>
          <dd>{unidadFuncionalEntity.codigo}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{unidadFuncionalEntity.nombre}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{unidadFuncionalEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/unidad-funcional" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unidad-funcional/${unidadFuncionalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UnidadFuncionalDetail;
