import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './invoice-billing-info.reducer';

export const InvoiceBillingInfo = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const invoiceBillingInfoList = useAppSelector(state => state.invoiceBillingInfo.entities);
  const loading = useAppSelector(state => state.invoiceBillingInfo.loading);
  const totalItems = useAppSelector(state => state.invoiceBillingInfo.totalItems);

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
      <h2 id="invoice-billing-info-heading" data-cy="InvoiceBillingInfoHeading">
        <Translate contentKey="approSysApp.invoiceBillingInfo.home.title">Invoice Billing Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="approSysApp.invoiceBillingInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/invoice-billing-info/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="approSysApp.invoiceBillingInfo.home.createLabel">Create new Invoice Billing Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {invoiceBillingInfoList && invoiceBillingInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.firstName">First Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firstName')} />
                </th>
                <th className="hand" onClick={sort('lastname')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.lastname">Lastname</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lastname')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.email">Email</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('phoneNum')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.phoneNum">Phone Num</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phoneNum')} />
                </th>
                <th className="hand" onClick={sort('address1')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.address1">Address 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('address1')} />
                </th>
                <th className="hand" onClick={sort('address2')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.address2">Address 2</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('address2')} />
                </th>
                <th className="hand" onClick={sort('city')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.city">City</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('city')} />
                </th>
                <th className="hand" onClick={sort('state')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.state">State</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('state')} />
                </th>
                <th className="hand" onClick={sort('county')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.county">County</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('county')} />
                </th>
                <th className="hand" onClick={sort('zipCode')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.zipCode">Zip Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('zipCode')} />
                </th>
                <th className="hand" onClick={sort('country')}>
                  <Translate contentKey="approSysApp.invoiceBillingInfo.country">Country</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('country')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {invoiceBillingInfoList.map((invoiceBillingInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/invoice-billing-info/${invoiceBillingInfo.id}`} color="link" size="sm">
                      {invoiceBillingInfo.id}
                    </Button>
                  </td>
                  <td>{invoiceBillingInfo.firstName}</td>
                  <td>{invoiceBillingInfo.lastname}</td>
                  <td>{invoiceBillingInfo.email}</td>
                  <td>{invoiceBillingInfo.phoneNum}</td>
                  <td>{invoiceBillingInfo.address1}</td>
                  <td>{invoiceBillingInfo.address2}</td>
                  <td>{invoiceBillingInfo.city}</td>
                  <td>{invoiceBillingInfo.state}</td>
                  <td>{invoiceBillingInfo.county}</td>
                  <td>{invoiceBillingInfo.zipCode}</td>
                  <td>{invoiceBillingInfo.country}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/invoice-billing-info/${invoiceBillingInfo.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/invoice-billing-info/${invoiceBillingInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (location.href = `/invoice-billing-info/${invoiceBillingInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="approSysApp.invoiceBillingInfo.home.notFound">No Invoice Billing Infos found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={invoiceBillingInfoList && invoiceBillingInfoList.length > 0 ? '' : 'd-none'}>
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

export default InvoiceBillingInfo;
