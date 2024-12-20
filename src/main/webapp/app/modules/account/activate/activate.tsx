import React, { useEffect } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Alert, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { activateAction, reset } from './activate.reducer';

const successAlert = (
  <Alert color="success">
    <strong>Su cuenta ha sido activada.</strong> Por favor,
    <Link to="/login" className="alert-link">
      iniciar sesión
    </Link>
    .
  </Alert>
);

const failureAlert = (
  <Alert color="danger">
    <strong>Su cuenta no pudo ser activada.</strong> Por favor, utilice el formulario de inscripción para registrarse.
  </Alert>
);

export const ActivatePage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();

  useEffect(() => {
    const key = searchParams.get('key');

    dispatch(activateAction(key));
    return () => {
      dispatch(reset());
    };
  }, []);

  const { activationSuccess, activationFailure } = useAppSelector(state => state.activate);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Activación</h1>
          {activationSuccess ? successAlert : undefined}
          {activationFailure ? failureAlert : undefined}
        </Col>
      </Row>
    </div>
  );
};

export default ActivatePage;
