import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Link } from 'react-router-dom';

import { NavItem, NavLink } from 'reactstrap';

export const ApproverMenu = () => (
  <NavItem>
    <NavLink tag={Link} to="/presupuesto" className="d-flex align-items-center" style={{ color: 'black' }}>
      <FontAwesomeIcon icon="file" />
      <span>Revisión Solicitudes</span>
    </NavLink>
  </NavItem>
);
