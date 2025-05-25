import React, { useEffect, useState } from "react";
import { Container, Navbar, Nav, NavDropdown, Image, Badge, Dropdown } from "react-bootstrap";
import cookie from 'react-cookies';
import { Link } from "react-router-dom";
import { FaUserCircle, FaBell } from "react-icons/fa";
import { authApis, endpoints } from "../../configs/Apis";

function Header() {
    const user = cookie.load("user");
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        const fetchNotifications = async () => {
            try {
                let res = await authApis().get(endpoints.get_notifications || "/secure/notifications");
                setNotifications(res.data);
            } catch (err) {
                console.error("Lỗi khi tải thông báo:", err);
            }
        };

        if (user) fetchNotifications();
    }, []);

    const logout = () => {
        cookie.remove("token");
        cookie.remove("user");
        window.location.reload();
    };

    const renderUserTitle = () => {
        if (!user) return "Tài khoản";

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
                    <img src="/logo.png" alt="Logo" width="90" style={{ maxHeight: '40px', objectFit: 'contain' }} />
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="main-navbar" />
                <Navbar.Collapse id="main-navbar">
                    <Nav className="ms-auto fw-semibold small align-items-center">

                        <Nav.Link href="#">Hỗ trợ</Nav.Link>
                        <Nav.Link href="#">Tiếng Việt</Nav.Link>

                        {/* 🔔 Thông báo */}
                        {user && (
                            <Dropdown align="end" className="ms-2">
                                <Dropdown.Toggle as={Nav.Link} id="notification-dropdown">
                                    <FaBell />
                                    {notifications.length > 0 && (
                                        <Badge bg="danger" pill className="ms-1">{notifications.length}</Badge>
                                    )}
                                </Dropdown.Toggle>

                                <Dropdown.Menu style={{ minWidth: '300px', maxHeight: '400px', overflowY: 'auto' }}>
                                    {notifications.length === 0 ? (
                                        <Dropdown.Item disabled>Không có thông báo</Dropdown.Item>
                                    ) : (
                                        notifications.map((n, i) => (
                                            <Dropdown.Item key={i}>
                                                <div><strong>{n.title}</strong></div>
                                                <div style={{ fontSize: "0.8rem" }}>{n.content}</div>
                                            </Dropdown.Item>
                                        ))
                                    )}
                                </Dropdown.Menu>
                            </Dropdown>
                        )}

                        {/* 👤 Người dùng */}
                        <NavDropdown title={renderUserTitle()} id="account-dropdown" align="end" className="ms-2">
                            {user ? (
                                <>
                                    <NavDropdown.Item as={Link} to="/profile">Thông tin cá nhân</NavDropdown.Item>
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
