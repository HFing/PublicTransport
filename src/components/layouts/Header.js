import React from "react";
import { Container, Navbar, Nav, NavDropdown, Image } from "react-bootstrap";
import cookie from 'react-cookies';
import { Link } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";

function Header() {
    const user = cookie.load("user");

    const logout = () => {
        cookie.remove("token");
        cookie.remove("user");
        window.location.reload();
    };

    const renderUserTitle = () => {
        if (!user) return "Tài khoản";

        console.log("User data:", user.lastName, user.firstName, user.email, user.avatarUrl);
        const name = user.fullName || user.email || "Người dùng";

        return (
            <span className="d-flex align-items-center gap-2">
                {user.avatarUrl ? (
                    <Image src={user.avatarUrl} roundedCircle width={26} height={26} style={{ objectFit: "cover" }} />
                ) : (
                    <FaUserCircle size={20} />
                )}
                <span className="d-none d-md-inline">{name}</span>
            </span>
        );
    };

    return (
        <Navbar expand="lg" bg="white" variant="light" fixed="top" className="shadow-sm py-2">
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
                    <Nav className="ms-auto fw-semibold small align-items-center">
                        <Nav.Link href="#">Vé xe khách</Nav.Link>
                        <Nav.Link href="#">Hủy vé</Nav.Link>
                        <Nav.Link href="#">Hỗ trợ</Nav.Link>
                        <Nav.Link href="#">Tiếng Việt</Nav.Link>

                        <NavDropdown
                            title={renderUserTitle()}
                            id="account-dropdown"
                            align="end"
                            className="ms-2"
                        >
                            {user ? (
                                <>
                                    <NavDropdown.Item href="#">Thông tin cá nhân</NavDropdown.Item>
                                    <NavDropdown.Item onClick={logout}>Đăng xuất</NavDropdown.Item>
                                </>
                            ) : (
                                <>
                                    <NavDropdown.Item as={Link} to="/login">Đăng nhập</NavDropdown.Item>
                                    <NavDropdown.Item as={Link} to="/register">Đăng ký</NavDropdown.Item>
                                </>
                            )}
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;
