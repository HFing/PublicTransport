// src/components/layouts/Footer.js
import React from "react";
import { Container } from "react-bootstrap";

function Footer() {
  return (
    <footer className="bg-light text-center py-3 mt-5">
      <Container>
        <p className="mb-0 text-muted">
          © {new Date().getFullYear()} Hệ thống Giao thông Công cộng. All rights reserved.
        </p>
      </Container>
    </footer>
  );
}

export default Footer;
