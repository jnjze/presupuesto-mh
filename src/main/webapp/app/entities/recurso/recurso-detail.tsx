import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recurso.reducer';

export const RecursoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recursoEntity = useAppSelector(state => state.recurso.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recursoDetailsHeading">Recurso</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recursoEntity.id}</dd>
          <dt>
            <span id="mes">Mes</span>
          </dt>
          <dd>{recursoEntity.mes}</dd>
          <dt>
            <span id="valor">Valor</span>
          </dt>
          <dd>{recursoEntity.valor}</dd>
          <dt>
            <span id="observacion">Observacion</span>
          </dt>
          <dd>{recursoEntity.observacion}</dd>
          <dt>Tipo Recurso</dt>
          <dd>{recursoEntity.tipoRecurso ? recursoEntity.tipoRecurso.id : ''}</dd>
          <dt>Presupuesto</dt>
          <dd>{recursoEntity.presupuesto ? recursoEntity.presupuesto.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/recurso" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recurso/${recursoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecursoDetail;
