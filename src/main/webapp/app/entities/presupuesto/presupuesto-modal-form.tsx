import React from 'react';
import { Form, FormGroup, Label, Input, Row, Col, Table } from 'reactstrap';

const PresupuestoModalForm = ({ data }) => {
  if (data.recursos) {
    return (
      <Form>
        <Row>
          <Col md="2">
            <FormGroup>
              <Label for="id" className="fw-bold">
                Consecutivo
              </Label>
              <Input type="text" id="id" value={data.id} readOnly />
            </FormGroup>
          </Col>

          <Col md="4">
            <FormGroup>
              <Label for="planNombre" className="fw-bold">
                Plan
              </Label>
              <Input type="text" id="planNombre" value={data.plan?.nombre || 'N/A'} readOnly />
            </FormGroup>
          </Col>
          <Col md="4">
            <FormGroup>
              <Label for="subPlanNombre" className="fw-bold">
                Sub Plan
              </Label>
              <Input type="text" id="subPlanNombre" value={data.subPlan?.nombre || 'N/A'} readOnly />
            </FormGroup>
          </Col>
          <Col md="2">
            <FormGroup>
              <Label for="id" className="fw-bold">
                Año
              </Label>
              <Input type="text" id="id" value={data.anio} readOnly />
            </FormGroup>
          </Col>
          <Row>
            <Col md="6">
              <Row>
                <Col md="6">
                  <FormGroup>
                    <Label for="fechaInicio" className="fw-bold">
                      Fecha Inicio
                    </Label>
                    <Input type="text" id="fechaInicio" value={data.fechaInicio} readOnly />
                  </FormGroup>
                </Col>
                <Col md="6">
                  <FormGroup>
                    <Label for="fechaFinal" className="fw-bold">
                      Fecha Final
                    </Label>
                    <Input type="text" id="fechaFinal" value={data.fechaFinal} readOnly />
                  </FormGroup>
                </Col>
                <Col md="6">
                  <FormGroup>
                    <Label for="fechaRegistro" className="fw-bold">
                      Fecha Registro
                    </Label>
                    <Input type="text" id="fechaRegistro" value={data.fechaRegistro} readOnly />
                  </FormGroup>
                </Col>
              </Row>
            </Col>
            <Col md="6">
              <FormGroup>
                <Label for="descripcionActividad" className="fw-bold">
                  Descripción Actividad
                </Label>
                <Input type="textarea" id="descripcionActividad" rows="6" value={data.descripcionActividad} readOnly />
              </FormGroup>
            </Col>
          </Row>
        </Row>
        <Row></Row>
        <Row>
          <Col md="6">
            <FormGroup>
              <Label for="nombreResponsable" className="fw-bold">
                Nombre Responsable
              </Label>
              <Input type="text" id="nombreResponsable" value={data.nombreResponsable} readOnly />
            </FormGroup>
          </Col>
          <Col md="6">
            <FormGroup>
              <Label for="correoResponsable" className="fw-bold">
                Correo Responsable
              </Label>
              <Input type="email" id="correoResponsable" value={data.correoResponsable} readOnly />
            </FormGroup>
          </Col>
        </Row>

        <FormGroup>
          <Label for="observaciones" className="fw-bold">
            Observaciones
          </Label>
          <Input type="textarea" id="observaciones" value={data.observaciones || 'N/A'} readOnly />
        </FormGroup>
        <FormGroup>
          <Label for="estado" className="fw-bold">
            Estado
          </Label>
          <p id="estado">
            {data.estado === 'APROBADO' ? 'Aprobado' : ''}
            {data.estado === 'RECHAZADO' ? 'Rechazado' : ''}
            {data.estado === 'SIN_ASIGNAR' ? 'Sin asignar' : ''}
            {data.estado === 'DEVOLUCION' ? 'Devolución' : ''}
          </p>
        </FormGroup>

        <h5>Recursos</h5>
        <Table striped>
          <thead>
            <tr>
              <th>Mes</th>
              <th>Tipo de Recurso</th>
              <th>Monto</th>
              <th>Observación</th>
            </tr>
          </thead>
          <tbody>
            {data.recursos.map((recurso, index) => (
              <tr key={index}>
                <td>{recurso.mes}</td>
                <td>
                  {recurso.tipoRecurso?.nombre || 'Sin asignar'} <br></br> {recurso.cargo && recurso.cargo.nombre}
                </td>
                <td>{recurso.valor}</td>
                <td>{recurso.observacion}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Form>
    );
  }
};

export default PresupuestoModalForm;
