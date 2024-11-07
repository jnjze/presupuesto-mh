import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad"></Col>
      <Col md="9">
        <h1 className="display-4">¡Bienvenido!</h1>
        <p className="lead ">Al aplicativo para el envio de solicitudes de presupuesto</p>
        <Col md="5">
          <p className="xskjq13Xcxq">
            Puede acceder al formulario haciendo click <Link to="/presupuesto-form">aquí</Link>{' '}
          </p>
        </Col>
        {account?.login ? (
          <Col md="4">
            <div>
              <Alert color="success">Está conectado como &quot;{account.login}&quot;.</Alert>
            </div>
          </Col>
        ) : (
          <div>
            <Alert color="warning">
              ¿Aún no tienes una cuenta?&nbsp;
              <Link to="/account/register" className="alert-link">
                Crea una
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
