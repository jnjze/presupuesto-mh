import React, { useEffect, useState } from 'react';
import { Button, Form, FormGroup, Label, Input, Alert, Row, Col, Table, InputGroup, InputGroupText } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity } from 'app/shared/reducers/presupuesto-slice';
import { getEntities as getPlans } from 'app/entities/plan/plan.reducer';
import { getEntities as getUnidadFuncionals } from 'app/entities/unidad-funcional/unidad-funcional.reducer';
import { getEntities as getRubros } from 'app/entities/rubro/rubro.reducer';
import { getEntities as getCentroCostos } from 'app/entities/centro-costo/centro-costo.reducer';
import { getEntityByPlan as getSubPlanByPlan } from 'app/entities/sub-plan/sub-plan.reducer';
import { getEntities as getTipoRecursos } from 'app/entities/tipo-recurso/tipo-recurso.reducer';
import { getEntities as getCargos } from 'app/entities/cargo/cargo.reducer';
import { useNavigate, useParams } from 'react-router';
import { getEntity, reset, updateEntity } from 'app/entities/presupuesto/presupuesto.reducer';
import { Estado } from 'app/shared/model/enumerations/estado.model';

import dayjs from 'dayjs';

const PresupuestoForm = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();
  const isEdit = id !== undefined;

  // Load required data
  useEffect(() => {
    if (isEdit) {
      dispatch(getEntity(id));
    }

    dispatch(getPlans({}));
    dispatch(getUnidadFuncionals({}));
    dispatch(getRubros({}));
    dispatch(getCentroCostos({}));
    dispatch(getTipoRecursos({}));
  }, [dispatch]);

  // State from redux
  const { loading, error } = useAppSelector(state => state.presupuestoPublic);
  const plans = useAppSelector(state => state.plan.entities);
  const subPlans = useAppSelector(state => state.subPlan.entities);
  const tipoRecursos = useAppSelector(state => state.tipoRecurso.entities);
  const cargos = useAppSelector(state => state.cargo.entities);
  const presupuestoEntity = useAppSelector(state => state.presupuesto.entity);

  // Local state for form values
  const [formState, setFormState] = useState({
    id: null,
    descripcionActividad: '',
    fechaInicio: '',
    fechaFinal: '',
    fechaRegistro: null,
    nombreResponsable: '',
    correoResponsable: '',
    plan: '',
    subPlan: '',
    anio: '',
    recursos: [],
  });

  const [recurso, setRecurso] = useState({
    mes: '',
    tipoRecurso: '',
    valor: '',
    observacion: '',
    cargo: '',
  });

  const navigate = useNavigate();

  useEffect(() => {
    if (presupuestoEntity && isEdit) {
      if (presupuestoEntity.plan) dispatch(getSubPlanByPlan(presupuestoEntity.plan.id));
      setFormState({
        id: presupuestoEntity.id,
        descripcionActividad: presupuestoEntity.descripcionActividad || '',
        fechaInicio: presupuestoEntity.fechaInicio || null,
        fechaFinal: presupuestoEntity.fechaFinal || null,
        fechaRegistro: presupuestoEntity.fechaRegistro,
        nombreResponsable: presupuestoEntity.nombreResponsable || '',
        correoResponsable: presupuestoEntity.correoResponsable || '',
        plan: presupuestoEntity.plan ? presupuestoEntity.plan.id.toString() : '',
        subPlan: presupuestoEntity.subPlan ? presupuestoEntity.subPlan.id.toString() : '',
        anio: presupuestoEntity.anio || '',
        recursos: presupuestoEntity.recursos || [],
      });
    }
  }, [presupuestoEntity, isEdit]);

  const [alertVisible, setAlertVisible] = useState(false);
  const [noRecursosAlert, setNoRecursosAlert] = useState(false);
  const [showCargos, setShowCargos] = useState(false);

  // Update form data on change
  const handleInputChange = event => {
    const { name, value } = event.target;
    setFormState(prevData => ({
      ...prevData,
      [name]: value,
    }));
    if (name === 'plan') {
      dispatch(getSubPlanByPlan(value));
    }
  };

  const handleRecursoInputChange = event => {
    const { name, value } = event.target;
    setRecurso({
      ...recurso,
      [name]: value,
    });
  };

  const handleTipoRecursoChange = e => {
    const tipoRecurso = tipoRecursos.find(tr => tr.id === Number(e.target.value));
    handleRecursoInputChange({ target: { name: 'tipoRecurso', value: tipoRecurso.id } });
    if (tipoRecurso.codigo === 'RH') {
      dispatch(getCargos({}));
      setShowCargos(true);
    }
  };

  const handleCargoChange = e => {
    const cargoSelected = cargos.find(tr => tr.id === Number(e.target.value));
    handleRecursoInputChange({ target: { name: 'cargo', value: cargoSelected.id } });
  };

  const addRecursoToList = () => {
    if (validateRecurso(recurso)) {
      const tipoRecurso = tipoRecursos.find(tr => tr.id === Number(recurso.tipoRecurso));
      const cargo = cargos.find(tr => tr.id === Number(recurso.cargo));

      const updatedRecurso = {
        ...recurso,
        tipoRecurso, // Only update the tipoRecurso here
        cargo,
      };

      setFormState({
        ...formState,
        recursos: [...formState.recursos, updatedRecurso],
      });
      setRecurso({ mes: '', tipoRecurso: '', valor: '', observacion: '', cargo: '' });
      setAlertVisible(false);
      setShowCargos(false);
    } else {
      setAlertVisible(true);
    }
  };

  const removeRecurso = index => {
    setFormState({
      ...formState,
      recursos: formState.recursos.filter((_, i) => i !== index),
    });
  };

  const validateRecurso = rec => {
    return rec.mes && rec.tipoRecurso && rec.tipoRecurso && rec.valor > 0 && rec.observacion;
  };

  const handleSubmit = event => {
    event.preventDefault();

    if (formState.recursos.length === 0) {
      setNoRecursosAlert(true);
      return;
    } else {
      setNoRecursosAlert(false);
    }
    const entity = {
      ...formState,
      plan: plans.find(it => it.id.toString() === formState.plan),
      subPlan: subPlans.find(it => it.id.toString() === formState.subPlan),
      estado: Estado.SIN_ASIGNAR,
    };
    if (isEdit) {
      dispatch(updateEntity(entity));
    } else {
      dispatch(createEntity(entity));
    }
    setFormState({
      id: null,
      descripcionActividad: '',
      fechaInicio: null,
      fechaFinal: null,
      fechaRegistro: null,
      nombreResponsable: '',
      correoResponsable: '',
      plan: '',
      subPlan: '',
      anio: '',
      recursos: [],
    });
    reset();
    navigate('/presupuesto-form');
  };

  const anios = [2025, 2026, 2027, 2028, 2029, 2030];
  const meses = [
    'Enero',
    'Febrero',
    'Marzo',
    'Abril',
    'Mayo',
    'Junio',
    'Julio',
    'Agosto',
    'Septiembre',
    'Octubre',
    'Noviembre',
    'Diciembre',
  ];

  return (
    <Form onSubmit={handleSubmit}>
      <Row className="justify-content-center">
        <Col md="8">
          <h3>Crear nueva solicitud</h3>
          <br></br>
          <Row>
            <Col md="4">
              <FormGroup>
                <Label for="plan">Plan</Label>
                <Input type="select" name="plan" id="plan" value={formState.plan} onChange={handleInputChange} required>
                  <option value="">Seleccione Plan</option>
                  {plans.map(plan => (
                    <option key={plan.id} value={plan.id}>
                      {plan.nombre}
                    </option>
                  ))}
                </Input>
              </FormGroup>
            </Col>
            <Col md="4">
              <FormGroup>
                <Label for="subPlan">Sub Plan</Label>
                <Input type="select" name="subPlan" id="subPlan" value={formState.subPlan} onChange={handleInputChange} required>
                  <option value="">Seleccione Sub Plan</option>
                  {subPlans.map(subPlan => (
                    <option key={subPlan.id} value={subPlan.id}>
                      {subPlan.nombre}
                    </option>
                  ))}
                </Input>
              </FormGroup>
            </Col>
            <Col md="4">
              <FormGroup>
                <Label for="anio">Año</Label>
                <Input type="select" name="anio" id="anio" value={formState.anio} onChange={handleInputChange} required>
                  <option value="">Seleccione Año</option>
                  {anios.map(anio => (
                    <option key={anio} value={anio}>
                      {anio}
                    </option>
                  ))}
                </Input>
              </FormGroup>
            </Col>
          </Row>

          <Row>
            <Col md="2">
              <FormGroup>
                <Label for="fechaInicio">Fecha Inicio</Label>
                <Input
                  type="date"
                  name="fechaInicio"
                  id="fechaInicio"
                  value={formState.fechaInicio || ''}
                  onChange={handleInputChange}
                  required
                />
              </FormGroup>
            </Col>
            <Col md="2">
              <FormGroup>
                <Label for="fechaFinal">Fecha Final</Label>
                <Input
                  type="date"
                  name="fechaFinal"
                  id="fechaFinal"
                  value={formState.fechaFinal || ''}
                  onChange={handleInputChange}
                  required
                />
              </FormGroup>
            </Col>
            <Col md="8">
              <FormGroup>
                <Label for="descripcionActividad">Descripcion Actividad</Label>
                <Input
                  type="textarea"
                  name="descripcionActividad"
                  id="descripcionActividad"
                  rows="4"
                  value={formState.descripcionActividad}
                  onChange={handleInputChange}
                />
              </FormGroup>
            </Col>
          </Row>
          <br></br>
          <h4>Recursos</h4>
          <Col md="12">
            {alertVisible && (
              <Alert color="danger" toggle={() => setAlertVisible(false)}>
                Por favor, complete todos los campos del recurso antes de agregar.
              </Alert>
            )}
          </Col>
          <Row>
            <Col md={6}>
              <FormGroup>
                <Input type="select" name="mes" id="mes" value={recurso.mes} onChange={handleRecursoInputChange}>
                  <option value="">Mes recurso</option>
                  {meses.map(mes => (
                    <option key={mes} value={mes}>
                      {mes}
                    </option>
                  ))}
                </Input>
              </FormGroup>
              <FormGroup>
                <Input type="select" name="tipoRecurso" id="tipoRecurso" value={recurso.tipoRecurso} onChange={handleTipoRecursoChange}>
                  <option value="">Tipo recurso</option>
                  {tipoRecursos.map(tipoRecurso => (
                    <option key={tipoRecurso.id} value={tipoRecurso.id}>
                      {tipoRecurso.nombre}
                    </option>
                  ))}
                </Input>
              </FormGroup>
              {showCargos && (
                <FormGroup>
                  <Input type="select" name="cargo" id="cargo" value={recurso.cargo} onChange={handleCargoChange}>
                    <option value="">Cargo</option>
                    {cargos.map(cargo => (
                      <option key={cargo.id} value={cargo.id}>
                        {cargo.nombre}
                      </option>
                    ))}
                  </Input>
                </FormGroup>
              )}
              <FormGroup>
                <InputGroup>
                  <InputGroupText>$</InputGroupText>
                  <Input type="number" name="valor" id="valor" value={recurso.valor} onChange={handleRecursoInputChange} />
                </InputGroup>
              </FormGroup>
            </Col>

            <Col md={6}>
              <FormGroup>
                <Label for="observacion">Descripción Recurso</Label>
                <Input
                  type="textarea"
                  name="observacion"
                  id="observacion"
                  value={recurso.observacion}
                  rows="4"
                  onChange={handleRecursoInputChange}
                />
              </FormGroup>
            </Col>
          </Row>
          <Button color="primary" onClick={addRecursoToList}>
            Agregar Recurso
          </Button>
          <br></br>
          <br></br>
          <Table striped>
            <thead>
              <tr>
                <th>Acciones</th>
                <th>Mes</th>
                <th>Tipo Recurso</th>
                <th>Monto</th>
                <th>Descripción Recurso</th>
              </tr>
            </thead>
            <tbody>
              {formState.recursos.map((recursox, index) => (
                <tr key={index}>
                  <td>
                    <Button color="danger" size="sm" onClick={() => removeRecurso(index)}>
                      Eliminar
                    </Button>
                  </td>
                  <td>{recursox.mes}</td>
                  <td>
                    {recursox.tipoRecurso.nombre} <br></br> {recursox.cargo && recursox.cargo.nombre}
                  </td>
                  <td>{`$${recursox.valor}`}</td>
                  <td>{recursox.observacion}</td>
                </tr>
              ))}
            </tbody>
          </Table>
          {noRecursosAlert && (
            <Alert color="danger" toggle={() => setNoRecursosAlert(false)}>
              Por favor, agregar recursos para esta solicitud.
            </Alert>
          )}
          <FormGroup>
            <Label for="nombreResponsable">Nombre Responsable</Label>
            <Input
              type="text"
              name="nombreResponsable"
              id="nombreResponsable"
              value={formState.nombreResponsable}
              onChange={handleInputChange}
              required
            />
          </FormGroup>

          <FormGroup>
            <Label for="correoResponsable">Correo Responsable</Label>
            <Input
              type="email"
              name="correoResponsable"
              id="correoResponsable"
              value={formState.correoResponsable}
              onChange={handleInputChange}
              required
            />
          </FormGroup>

          <Button color="primary" type="submit" disabled={loading}>
            {loading ? 'Enviando...' : 'Enviar'}
          </Button>
        </Col>
      </Row>
    </Form>
  );
};

export default PresupuestoForm;
