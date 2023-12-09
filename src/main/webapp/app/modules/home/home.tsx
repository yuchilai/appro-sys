import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button, Card } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import { Clock } from 'app/shared/clock/clock';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const isUser = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.USER]));
  const isApprover = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.APPROVER]));

  return (
    <>
      {!isAuthenticated ? (
        <Row>
          <Col md="3" className="pad">
            <span className="hipster rounded" />
          </Col>
          <Col md="9">
            <h1 className="display-4">
              <Translate contentKey="home.title">Welcome to ApproSys!</Translate>
            </h1>
            <p className="lead">
              <Translate contentKey="home.subtitle">
                ApproSys is a tailored solution designed to streamline the project approval and invoicing process for freelance programming
                consultants.
              </Translate>
            </p>
            <div>
              <Alert color="warning">
                <Translate contentKey="global.messages.info.authenticated.prefix">Please sign in </Translate>

                <Link to="/login" className="alert-link">
                  <Translate contentKey="global.messages.info.authenticated.link">here</Translate>
                </Link>
                <Translate contentKey="global.messages.info.authenticated.suffix">
                  . If you are an approver, you should request your designated client liaison to assign you as their official approver.
                </Translate>
              </Alert>

              <Alert color="warning">
                <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
                <Link to="/account/register" className="alert-link">
                  <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
                </Link>
              </Alert>
            </div>
            <p>
              <Translate contentKey="home.like">If you like my project, do not forget to give us a star on</Translate>{' '}
              <a href="https://github.com/yuchilai" target="_blank" rel="noopener noreferrer">
                GitHub
              </a>
              !
            </p>
          </Col>
        </Row>
      ) : (
        <Row>
          <Col md="4" className="pad">
            <h1 className="display-5">
              <Translate contentKey="home.welcome">Welcome</Translate> {account.login}
            </h1>
            <h2 className="display-6">
              <Clock />
            </h2>
          </Col>
          <Col md="4">
            <Card>
              <Button tag={Link} to="/address" size="lg" replace color="info" data-cy="">
                <FontAwesomeIcon icon="briefcase" />{' '}
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
            </Card>
          </Col>
          <Col md="4">
            <Card>
              <Button tag={Link} to="/address" size="lg" replace color="info" data-cy="">
                <FontAwesomeIcon icon="briefcase" />{' '}
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
            </Card>
          </Col>
        </Row>
      )}
    </>
  );
};

export default Home;
