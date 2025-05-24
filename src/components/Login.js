import React, { useState, useContext } from "react";
import { Form, Button, Container, Row, Col, Alert, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import cookie from "react-cookies";

import API, { authApis, endpoints } from "../configs/Apis";
import { MyDispatcherContext } from "../configs/MyContexts";

const Login = () => {
    const [credentials, setCredentials] = useState({ email: "", password: "" });
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const dispatch = useContext(MyDispatcherContext);

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const res = await API.post(endpoints["login"], credentials);
            const token = res.data.token;
            cookie.save("token", token, { path: "/" });

            const userRes = await authApis().get(endpoints["current_user"]);
            const user = userRes.data;

            cookie.save("user", JSON.stringify(user), { path: "/" });

            dispatch({ type: "login", payload: user });

            navigate("/");
        } catch (err) {
            if (err.response) {
                const status = err.response.status;
                if (status === 401) {
                    setError("Sai email hoặc mật khẩu!");
                } else if (status === 403) {
                    setError("Tài khoản của bạn đã bị khóa!");
                } else {
                    setError("Lỗi đăng nhập, vui lòng thử lại sau!");
                }
            } else {
                setError("Không thể kết nối đến server.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="py-5" style={{ marginTop: "80px" }}>
            <Row className="justify-content-center">
                <Col md={6} className="shadow p-4 rounded bg-white">
                    <h3 className="text-center mb-4 fw-bold">Đăng nhập</h3>

                    {error && <Alert variant="danger">{error}</Alert>}

                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={credentials.email}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>

                        <Form.Group className="mb-4">
                            <Form.Label>Mật khẩu</Form.Label>
                            <Form.Control
                                type="password"
                                name="password"
                                value={credentials.password}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>

                        <Button type="submit" className="w-100 fw-bold" disabled={loading}>
                            {loading ? (
                                <>
                                    <Spinner animation="border" size="sm" /> Đang đăng nhập...
                                </>
                            ) : (
                                "Đăng nhập"
                            )}
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default Login;
