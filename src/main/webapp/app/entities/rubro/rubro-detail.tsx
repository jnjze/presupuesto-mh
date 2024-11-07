import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rubro.reducer';

export const RubroDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rubroEntity = useAppSelector(state => state.rubro.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rubroDetailsHeading">Rubro</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{rubroEntity.id}</dd>
          <dt>
            <span id="codigo">Codigo</span>
          </dt>
          <dd>{rubroEntity.codigo}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{rubroEntity.nombre}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{rubroEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/rubro" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rubro/${rubroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RubroDetail;
