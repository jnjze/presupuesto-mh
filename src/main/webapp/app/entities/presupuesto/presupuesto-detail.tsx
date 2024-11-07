import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './presupuesto.reducer';
import PresupuestoModalForm from './presupuesto-modal-form';

export const PresupuestoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const presupuestoEntity = useAppSelector(state => state.presupuesto.entity);
  return (
    <Row className="justify-content-center">
      <Col md="8">
        <h2 data-cy="presupuestoDetailsHeading">Detalle Solicitud</h2>
        <PresupuestoModalForm data={presupuestoEntity} />
        <Button tag={Link} to="/presupuesto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/presupuesto/${presupuestoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PresupuestoDetail;
