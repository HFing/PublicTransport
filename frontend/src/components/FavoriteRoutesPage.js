import React, { useState, useEffect } from "react";
import { Container, Row, Col, Card, Badge, Spinner, Image, Button } from "react-bootstrap";
import {
    MdDirectionsBus,
    MdOutlineArrowForward,
    MdAccessTime,
    MdToday,
    MdEmail,
    MdPerson,
    MdDelete,
    MdNotificationsActive
} from "react-icons/md";
import { authApis, endpoints } from "../configs/Apis";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const FavoriteRoutesPage = () => {
    const [user, setUser] = useState(null);
    const [routes, setRoutes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [notificationSettings, setNotificationSettings] = useState({});

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const res = await authApis().get(endpoints.current_user);
                setUser(res.data);
            } catch (err) {
                console.error("Lỗi khi lấy thông tin người dùng:", err);
            }
        };
        fetchUser();
    }, []);

    useEffect(() => {
        if (user) {
            fetchFavoriteRoutes();
        }
    }, [user]);

    const fetchFavoriteRoutes = async () => {
        try {
            const res = await authApis().get(endpoints.get_favorite_routes);
            const data = res.data || [];
            setRoutes(data);

            const notifMap = {};
            data.forEach(route => {
                notifMap[route.routeId] = route.notifyOnChanges;
            });
            setNotificationSettings(notifMap);
        } catch (err) {
            console.error("Lỗi khi lấy danh sách tuyến yêu thích:", err);
        } finally {
            setLoading(false);
        }
    };

    const handleRemoveFavorite = async (routeId) => {
        try {
            await authApis().post(endpoints.remove_favorite_route, null, { params: { routeId } });
            toast.info("Đã xoá tuyến khỏi danh sách yêu thích", { autoClose: 4000 });
            fetchFavoriteRoutes();
        } catch (err) {
            toast.error("Lỗi khi xoá tuyến yêu thích", { autoClose: 4000 });
        }
    };

    const handleToggleNotification = async (routeId) => {
        const current = notificationSettings[routeId];
        try {
            await authApis().post(endpoints.update_notification, null, {
                params: { routeId, notifyOnChanges: !current }
            });
            setNotificationSettings(prev => ({ ...prev, [routeId]: !current }));
            toast.success(`Đã ${!current ? "bật" : "tắt"} thông báo cho tuyến này`, { autoClose: 4000 });
        } catch (err) {
            toast.error("Lỗi khi cập nhật thông báo", { autoClose: 4000 });
        }
    };

    const formatTime = (t) => t?.slice(0, 5);
    const formatDate = (dateStr) =>
        new Date(dateStr).toLocaleDateString("vi-VN", {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "numeric"
        });

    return (
        <Container className="py-5" style={{ paddingTop: '120px' }}>
            <ToastContainer />

            <h2 className="fw-bold mb-4">Thông tin cá nhân</h2>

            {user && (
                <Card className="mb-5 p-3 border shadow-sm bg-light">
                    <Card.Body className="d-flex align-items-center gap-4">
                        <Image
                            src={user.avatarUrl}
                            roundedCircle
                            width={80}
                            height={80}
                            style={{ objectFit: "cover", border: "2px solid #ccc" }}
                        />
                        <div>
                            <div className="d-flex align-items-center mb-2">
                                <MdPerson size={22} className="me-2 text-primary" />
                                <h5 className="mb-0">
                                    {user.fullName || `${user.firstName} ${user.lastName}`}
                                </h5>
                            </div>
                            <div className="d-flex align-items-center">
                                <MdEmail size={20} className="me-2 text-secondary" />
                                <span>{user.email}</span>
                            </div>
                        </div>
                    </Card.Body>
                </Card>
            )}

            <h4 className="fw-semibold mb-3">Tuyến đường yêu thích</h4>

            {loading ? (
                <div className="text-center py-4">
                    <Spinner animation="border" variant="primary" />
                </div>
            ) : routes.length === 0 ? (
                <p>Chưa có tuyến đường yêu thích nào.</p>
            ) : (
                <Row className="g-4">
                    {routes.map((route, idx) => (
                        <Col key={idx} xl={4} lg={6} md={12}>
                            <Card className="shadow rounded-4 p-3">
                                <Card.Body>
                                    <div className="d-flex justify-content-between align-items-center mb-2">
                                        <div className="d-flex align-items-center">
                                            <MdDirectionsBus className="text-primary me-2" size={24} />
                                            <h5 className="mb-0 fw-semibold">{route.routeName}</h5>
                                        </div>
                                        <div className="d-flex align-items-center gap-2">
                                            <Button
                                                variant={notificationSettings[route.routeId] ? "primary" : "outline-primary"}
                                                size="sm"
                                                title={notificationSettings[route.routeId] ? "Đã bật thông báo" : "Chưa bật thông báo"}
                                                onClick={() => handleToggleNotification(route.routeId)}
                                            >
                                                <MdNotificationsActive
                                                    size={18}
                                                    color={notificationSettings[route.routeId] ? "#ffc107" : "#6c757d"}
                                                />
                                            </Button>
                                            <Button
                                                variant="outline-danger"
                                                size="sm"
                                                title="Xoá khỏi yêu thích"
                                                onClick={() => handleRemoveFavorite(route.routeId)}
                                            >
                                                <MdDelete size={20} />
                                            </Button>
                                        </div>
                                    </div>
                                    <p className="text-muted mb-2">
                                        <MdOutlineArrowForward className="me-1" />
                                        Phương tiện: <strong>{route.transportType}</strong>
                                    </p>
                                    {route.schedules?.length > 0 && (
                                        <div className="mb-2">
                                            <div className="fw-semibold mb-1">
                                                <MdToday className="me-1" /> {formatDate(route.schedules[0].day)}
                                            </div>
                                            <div className="d-flex flex-wrap gap-2">
                                                {route.schedules.map((s, i) => (
                                                    <Badge key={i} bg="light" text="dark" className="px-2 py-1">
                                                        <MdAccessTime className="me-1" /> {formatTime(s.startTime)} - {formatTime(s.endTime)}
                                                    </Badge>
                                                ))}
                                            </div>
                                        </div>
                                    )}
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}
        </Container>
    );
};

export default FavoriteRoutesPage;
