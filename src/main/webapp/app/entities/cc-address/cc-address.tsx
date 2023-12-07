import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './cc-address.reducer';

export const CCAddress = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const cCAddressList = useAppSelector(state => state.cCAddress.entities);
  const loading = useAppSelector(state => state.cCAddress.loading);
  const totalItems = useAppSelector(state => state.cCAddress.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="cc-address-heading" data-cy="CCAddressHeading">
        <Translate contentKey="approSysApp.cCAddress.home.title">CC Addresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="approSysApp.cCAddress.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/cc-address/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="approSysApp.cCAddress.home.createLabel">Create new CC Address</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cCAddressList && cCAddressList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="approSysApp.cCAddress.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('address1')}>
                  <Translate contentKey="approSysApp.cCAddress.address1">Address 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('address1')} />
                </th>
                <th className="hand" onClick={sort('address2')}>
                  <Translate contentKey="approSysApp.cCAddress.address2">Address 2</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('address2')} />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="approSysApp.cCAddress.city">City</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('city')} />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="approSysApp.cCAddress.state">State</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('state')} />
                </th>
                <th className="hand" onClick={sort('county')}>
                  <Translate contentKey="approSysApp.cCAddress.county">County</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('county')} />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="approSysApp.cCAddress.zipCode">Zip Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('zipCode')} />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="approSysApp.cCAddress.country">Country</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('country')} />
                </th>
                <th className="hand" onClick={sort('isUsedForInvoice')}>
                  <Translate contentKey="approSysApp.cCAddress.isUsedForInvoice">Is Used For Invoice</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('isUsedForInvoice')} />
                </th>
                <th>
                  <Translate contentKey="approSysApp.cCAddress.clientCompany">Client Company</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cCAddressList.map((cCAddress, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cc-address/${cCAddress.id}`} color="link" size="sm">
                      {cCAddress.id}
                    </Button>
                  </td>
                  <td>{cCAddress.address1}</td>
                  <td>{cCAddress.address2}</td>
                  <td>{cCAddress.city}</td>
                  <td>{cCAddress.state}</td>
                  <td>{cCAddress.county}</td>
                  <td>{cCAddress.zipCode}</td>
                  <td>{cCAddress.country}</td>
                  <td>{cCAddress.isUsedForInvoice ? 'true' : 'false'}</td>
                  <td>
                    {cCAddress.clientCompany ? (
                      <Link to={`/client-company/${cCAddress.clientCompany.id}`}>{cCAddress.clientCompany.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/cc-address/${cCAddress.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/cc-address/${cCAddress.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (location.href = `/cc-address/${cCAddress.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="approSysApp.cCAddress.home.notFound">No CC Addresses found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={cCAddressList && cCAddressList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default CCAddress;
