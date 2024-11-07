import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12" style={{ textAlign: 'center', marginTop: '10px' }}>
        <p className="center">Medihelp Services 2024</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
