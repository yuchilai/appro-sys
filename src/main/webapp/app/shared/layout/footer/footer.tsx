import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <div className="footer page-content">
      <Row>
        <Col md="12">
          <p>
            © {currentYear} YuChi Lai. <Translate contentKey="footer.rights">All rights reserved.</Translate>
          </p>
        </Col>
      </Row>
    </div>
  );
};

export default Footer;
