import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Col, Input, Modal, ModalBody, ModalFooter, ModalHeader, Row, Table } from 'reactstrap';
import { TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, updateEntity } from './presupuesto.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import PresupuestoModalForm from './presupuesto-modal-form';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export const Presupuesto = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'estado'), pageLocation.search));

  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isApprover = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.APPROVER]));

  const presupuestoList = useAppSelector(state => state.presupuesto.entities);
  const loading = useAppSelector(state => state.presupuesto.loading);

  const [modalPresupuestoInfo, setModalPresupuestoInfo] = useState(false);
  const [modalApprove, setModalApprove] = useState(false);
  const [modalReject, setModalReject] = useState(false);
  const [modalReturn, setModalReturn] = useState(false);

  const [selectedPresupuesto, setSelectedPresupuesto] = useState(null);
  const [observaciones, setObservaciones] = useState('');

  // Filter state for 'estado'
  const [estadoFilter, setEstadoFilter] = useState('');

  const handleEstadoFilterChange = event => {
    setEstadoFilter(event.target.value);
  };

  const getFilteredEntities = () => {
    return presupuestoList.filter(presupuesto => {
      return estadoFilter === '' || presupuesto.estado === estadoFilter;
    });
  };

  const filteredPresupuestoList = getFilteredEntities();

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},desc`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, 'sortState.sort']);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  // Handlers for modals
  const toggleApproveModal = () => setModalApprove(!modalApprove);
  const togglePresupuestoInfo = () => setModalPresupuestoInfo(!modalPresupuestoInfo);

  const toggleRejectModal = () => setModalReject(!modalReject);
  const toggleReturnModal = () => setModalReturn(!modalReturn);

  const openPresupuestoInfo = presupuesto => {
    setSelectedPresupuesto(presupuesto);
    togglePresupuestoInfo();
  };

  const handleApprove = () => {
    setObservaciones(selectedPresupuesto?.observaciones || '');
    toggleApproveModal();
  };

  const handleReject = () => {
    toggleRejectModal();
  };

  const handleReturn = () => {
    toggleReturnModal();
  };

  const handleCloseAllReturn = () => {
    const entity = {
      ...selectedPresupuesto,
      estado: Estado.DEVOLUCION,
      observaciones,
    };

    dispatch(updateEntity(entity));
    toggleReturnModal();
    togglePresupuestoInfo();
  };
  const handleCloseAllReject = () => {
    const entity = {
      ...selectedPresupuesto,
      estado: Estado.RECHAZADO,
      observaciones,
    };

    dispatch(updateEntity(entity));
    toggleRejectModal();
    togglePresupuestoInfo();
  };

  const handleCloseAllApprove = () => {
    const entity = {
      ...selectedPresupuesto,
      estado: Estado.APROBADO,
      observaciones,
    };

    dispatch(updateEntity(entity));
    toggleApproveModal();
    togglePresupuestoInfo();
  };

  return (
    <div>
      <h3 id="presupuesto-heading" data-cy="PresupuestoHeading" className="mt-4">
        Revisión Solicitudes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          {isAdmin && (
            <Link to="/presupuesto/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp; Crear nuevo Presupuesto
            </Link>
          )}
        </div>
      </h3>
      <div className="table-responsive">
        <Row className="mb-4">
          <Col md="3">
            <label htmlFor="estado-filter" className="mt-3">
              Filtrar Estado
            </label>
            <div>
              <select id="estado-filter" value={estadoFilter} onChange={handleEstadoFilterChange} className="form-select mt-1">
                <option value="">Todos</option>
                <option value="APROBADO">Aprobado</option>
                <option value="RECHAZADO">Rechazado</option>
                <option value="DEVOLUCION">Devolución</option>
                <option value="SIN_ASIGNAR">Sin Asignar</option>
              </select>
            </div>
          </Col>
        </Row>

        {filteredPresupuestoList && filteredPresupuestoList.length > 0 ? (
          <Table responsive size="sm">
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  Consecutivo <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>

                <th className="hand" onClick={sort('fechaRegistro')}>
                  Fecha Registro <FontAwesomeIcon icon={getSortIconByFieldName('fechaRegistro')} />
                </th>
                <th className="hand" onClick={sort('fechaInicio')}>
                  Fecha Inicio <FontAwesomeIcon icon={getSortIconByFieldName('fechaInicio')} />
                </th>
                <th className="hand" onClick={sort('fechaFinal')}>
                  Fecha Final <FontAwesomeIcon icon={getSortIconByFieldName('fechaFinal')} />
                </th>
                <th>
                  Plan <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Sub Plan <FontAwesomeIcon icon="sort" />
                </th>
                {isAdmin && (
                  <th>
                    Unidad Funcional <FontAwesomeIcon icon="sort" />
                  </th>
                )}
                {isAdmin && (
                  <th>
                    Rubro <FontAwesomeIcon icon="sort" />
                  </th>
                )}
                {isAdmin && (
                  <th>
                    Centro Costo <FontAwesomeIcon icon="sort" />
                  </th>
                )}
                <th className="hand" onClick={sort('nombreResponsable')}>
                  Responsable <FontAwesomeIcon icon={getSortIconByFieldName('nombreResponsable')} />
                </th>
                <th className="hand" onClick={sort('correoResponsable')}>
                  Correo Responsable <FontAwesomeIcon icon={getSortIconByFieldName('correoResponsable')} />
                </th>
                <th className="hand" onClick={sort('estado')}>
                  Estado <FontAwesomeIcon icon={getSortIconByFieldName('estado')} />
                </th>
                <th className="text-end">Acción</th>
              </tr>
            </thead>
            <tbody>
              {filteredPresupuestoList.map((presupuesto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{presupuesto.id}</td>
                  <td>
                    {presupuesto.fechaRegistro ? (
                      <TextFormat type="date" value={presupuesto.fechaRegistro} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {presupuesto.fechaInicio ? (
                      <TextFormat type="date" value={presupuesto.fechaInicio} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {presupuesto.fechaFinal ? (
                      <TextFormat type="date" value={presupuesto.fechaFinal} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{presupuesto.plan ? presupuesto.plan.nombre : ''}</td>
                  <td>{presupuesto.subPlan ? presupuesto.subPlan.nombre : ''}</td>
                  {isAdmin && <td>{presupuesto.unidadFuncional ? presupuesto.unidadFuncional.nombre : ''}</td>}
                  {isAdmin && <td>{presupuesto.rubro ? presupuesto.rubro.nombre : ''}</td>}
                  {isAdmin && <td>{presupuesto.centroCosto ? presupuesto.centroCosto.nombre : ''}</td>}
                  <td>{presupuesto.nombreResponsable}</td>
                  <td>{presupuesto.correoResponsable}</td>
                  <td>
                    {presupuesto.estado === 'APROBADO' ? 'Aprobado' : ''}
                    {presupuesto.estado === 'RECHAZADO' ? 'Rechazado' : ''}
                    {presupuesto.estado === 'SIN_ASIGNAR' ? 'Sin asignar' : ''}
                    {presupuesto.estado === 'DEVOLUCION' ? 'Devolución' : ''}
                  </td>

                  <td className="text-end" style={{ width: '180px' }}>
                    <div className="flex-btn-group-container">
                      {isAdmin && (
                        <Button
                          tag={Link}
                          to={`/presupuesto/${presupuesto.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                          className="p-2 min-height"
                        >
                          <FontAwesomeIcon icon="eye" size="sm" />
                        </Button>
                      )}
                      {isAdmin && (
                        <Button
                          onClick={() => (window.location.href = `/presupuesto/${presupuesto.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                          className="p-2 min-height"
                        >
                          <FontAwesomeIcon icon="trash" size="sm" />
                        </Button>
                      )}
                      {isApprover && presupuesto.estado === 'SIN_ASIGNAR' && (
                        <div>
                          <Button
                            color="success"
                            size="sm"
                            data-cy="entityDeleteButton"
                            className="p-2 min-height"
                            onClick={() => openPresupuestoInfo(presupuesto)}
                          >
                            <FontAwesomeIcon icon="eye" size="sm" />
                          </Button>
                        </div>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningúna solicitud encontrada</div>
        )}
      </div>

      {/* Presupuesto Modal */}
      <Modal isOpen={modalPresupuestoInfo} toggle={togglePresupuestoInfo} size="lg" backdrop={true}>
        <ModalHeader toggle={togglePresupuestoInfo}>Aprobar Presupuesto</ModalHeader>
        <ModalBody>
          <PresupuestoModalForm data={selectedPresupuesto} />
        </ModalBody>
        <ModalFooter>
          <Button color="success" onClick={handleApprove}>
            Aprobar
          </Button>
          <Button color="warning" onClick={handleReturn}>
            Devolver
          </Button>
          <Button color="danger" onClick={handleReject}>
            Rechazar
          </Button>
          <Button color="secondary" onClick={togglePresupuestoInfo}>
            Cancelar
          </Button>
        </ModalFooter>
      </Modal>

      {/* Approve Modal */}
      <Modal isOpen={modalApprove} toggle={toggleApproveModal}>
        <ModalHeader toggle={toggleApproveModal}>Aprobar Presupuesto</ModalHeader>
        <ModalBody>
          ¿Estás seguro de que deseas aprobar este presupuesto?
          <br></br>
          <br></br>
          Observaciones
          <Input type="textarea" rows="4" value={observaciones} onChange={e => setObservaciones(e.target.value)} />
        </ModalBody>
        <ModalFooter>
          <Button color="success" onClick={handleCloseAllApprove}>
            Aprobar
          </Button>{' '}
          <Button color="secondary" onClick={toggleApproveModal}>
            Cancelar
          </Button>
        </ModalFooter>
      </Modal>

      {/* Reject Modal */}
      <Modal isOpen={modalReject} toggle={toggleRejectModal}>
        <ModalHeader toggle={toggleRejectModal}>Rechazar Presupuesto</ModalHeader>
        <ModalBody>
          ¿Estás seguro de que deseas rechazar este presupuesto?
          <br></br>
          <br></br>
          Observaciones
          <Input type="textarea" rows="4" value={observaciones} onChange={e => setObservaciones(e.target.value)} />
        </ModalBody>
        <ModalFooter>
          <Button color="danger" onClick={handleCloseAllReject}>
            Rechazar
          </Button>{' '}
          <Button color="secondary" onClick={toggleRejectModal}>
            Cancelar
          </Button>
        </ModalFooter>
      </Modal>

      <Modal isOpen={modalReturn} toggle={toggleReturnModal}>
        <ModalHeader toggle={toggleReturnModal}>Devolver solicitud</ModalHeader>
        <ModalBody>
          ¿Estás seguro de que deseas devolver esta solicitud para su correción?
          <br></br>
          <br></br>
          Observaciones
          <Input type="textarea" rows="4" value={observaciones} onChange={e => setObservaciones(e.target.value)} />
        </ModalBody>
        <ModalFooter>
          <Button color="warning" onClick={handleCloseAllReturn}>
            Devolver
          </Button>{' '}
          <Button color="secondary" onClick={toggleReturnModal}>
            Cancelar
          </Button>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default Presupuesto;
