
import React from "react";
import { Container, Navbar, Nav } from "react-bootstrap";

function Header() {
    return (
        <Navbar
            expand="lg"
            bg="white"
            variant="light"
            fixed="top"
            className="shadow-sm py-2"
        >
            <Container>
                <Navbar.Brand href="/" className="d-flex align-items-center">
                    <img
                        src="/logo.png"
                        alt="Logo"
                        width="90"
                        style={{ maxHeight: '40px', objectFit: 'contain' }}
                    />
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="main-navbar" />
                <Navbar.Collapse id="main-navbar">
                    <Nav className="ms-auto fw-semibold small">
                        <Nav.Link href="#">Vé xe khách</Nav.Link>
                        <Nav.Link href="#">Hủy vé</Nav.Link>
                        <Nav.Link href="#">Hỗ trợ</Nav.Link>
                        <Nav.Link href="#">Tiếng Việt</Nav.Link>
                        <Nav.Link href="#">Tài khoản</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>

    );
}

export default Header;
