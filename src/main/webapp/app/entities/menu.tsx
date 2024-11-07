import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/presupuesto">
        Presupuesto
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan">
        Plan
      </MenuItem>
      <MenuItem icon="asterisk" to="/sub-plan">
        Sub Plan
      </MenuItem>
      <MenuItem icon="asterisk" to="/unidad-funcional">
        Unidad Funcional
      </MenuItem>
      <MenuItem icon="asterisk" to="/rubro">
        Rubro
      </MenuItem>
      <MenuItem icon="asterisk" to="/centro-costo">
        Centro Costo
      </MenuItem>
      <MenuItem icon="asterisk" to="/recurso">
        Recurso
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-recurso">
        Tipo Recurso
      </MenuItem>
      <MenuItem icon="asterisk" to="/cargo">
        Cargo
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
