import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sub-plan.reducer';

export const SubPlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subPlanEntity = useAppSelector(state => state.subPlan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subPlanDetailsHeading">Sub Plan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subPlanEntity.id}</dd>
          <dt>
            <span id="codigo">Codigo</span>
          </dt>
          <dd>{subPlanEntity.codigo}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{subPlanEntity.nombre}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{subPlanEntity.descripcion}</dd>
          <dt>Plan</dt>
          <dd>{subPlanEntity.plan ? subPlanEntity.plan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sub-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sub-plan/${subPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubPlanDetail;
