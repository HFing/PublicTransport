import React, { useState } from "react";
import { Form, Button, Container, Row, Col, Alert, Spinner } from "react-bootstrap";
import API, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";

function Register() {
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        role: "user"
    });
    const [avatar, setAvatar] = useState(null);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (form.password !== form.confirmPassword) {
            setError("Mật khẩu xác nhận không khớp!");
            return;
        }

        const formData = new FormData();
        for (let key of ["firstName", "lastName", "email", "password", "role"]) {
            formData.append(key, form[key]);
        }
        if (avatar) formData.append("avatar", avatar);

        setLoading(true);
        try {
            await API.post(endpoints["register"], formData);
            setSuccess(true);
            setError("");
            setTimeout(() => navigate("/login"), 1500); // chuyển sau 1.5s
        } catch (err) {
            setError("Lỗi đăng ký tài khoản!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="py-5" style={{ marginTop: "80px" }}>
            <Row className="justify-content-center">
                <Col md={6} className="shadow p-4 rounded bg-white">
                    <h3 className="text-center mb-4 fw-bold">Đăng ký tài khoản</h3>
                    {success && <Alert variant="success">Đăng ký thành công! Đang chuyển hướng...</Alert>}
                    {error && <Alert variant="danger">{error}</Alert>}
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Họ</Form.Label>
                            <Form.Control type="text" name="firstName" value={form.firstName} onChange={handleChange} required />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Tên</Form.Label>
                            <Form.Control type="text" name="lastName" value={form.lastName} onChange={handleChange} required />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" name="email" value={form.email} onChange={handleChange} required />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Mật khẩu</Form.Label>
                            <Form.Control type="password" name="password" value={form.password} onChange={handleChange} required />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Xác nhận mật khẩu</Form.Label>
                            <Form.Control type="password" name="confirmPassword" value={form.confirmPassword} onChange={handleChange} required />
                        </Form.Group>
                        <Form.Group className="mb-4">
                            <Form.Label>Ảnh đại diện (tuỳ chọn)</Form.Label>
                            <Form.Control type="file" onChange={(e) => setAvatar(e.target.files[0])} />
                        </Form.Group>
                        <Button type="submit" className="w-100 fw-bold" variant="primary" disabled={loading}>
                            {loading ? <><Spinner animation="border" size="sm" /> Đang xử lý...</> : "Đăng ký"}
                        </Button>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default Register;
