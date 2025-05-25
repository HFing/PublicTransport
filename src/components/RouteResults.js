import React, { useState, useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Container, Row, Col, Card, Button, Modal, Badge, Form, Spinner } from "react-bootstrap";
import { MdDirectionsBus, MdOutlineArrowForward, MdAccessTime, MdFavoriteBorder, MdFavorite, MdToday, MdReport } from "react-icons/md";
import { MapContainer, TileLayer, Marker, Popup, Polyline } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import markerIcon2x from "leaflet/dist/images/marker-icon-2x.png";
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";
import "./css/RouteResults.css";
import API, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

L.Icon.Default.mergeOptions({
    iconRetinaUrl: markerIcon2x,
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
});

const RouteResults = () => {
    const location = useLocation();
    const routes = location.state?.routes || [];
    const [showModal, setShowModal] = useState(false);
    const [selectedRoute, setSelectedRoute] = useState(null);
    const [favoriteRouteIds, setFavoriteRouteIds] = useState([]);
    const [showReportModal, setShowReportModal] = useState(false);
    const [reportData, setReportData] = useState({ location: '', description: '', image: null });
    const [reportLoading, setReportLoading] = useState(false);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const fetchFavorites = async () => {
            try {
                const res = await authApis().get(endpoints.get_favorite_routes);
                const ids = res.data.map(item => item.routeId);
                setFavoriteRouteIds(ids);
            } catch (err) {
                console.error("Lỗi khi lấy danh sách yêu thích:", err);
            }
        };
        fetchFavorites();
    }, []);

    const formatTime = (t) => t?.slice(0, 5);
    const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString("vi-VN", {
        weekday: "long", year: "numeric", month: "long", day: "numeric"
    });

    const openDetail = (route) => {
        setSelectedRoute(route);
        setShowModal(true);
    };

    const handleToggleFavorite = async (routeId) => {
        const isFavorite = favoriteRouteIds.includes(routeId);
        try {
            if (isFavorite) {
                await authApis().post(endpoints.remove_favorite_route, null, { params: { routeId } });
                setFavoriteRouteIds(prev => prev.filter(id => id !== routeId));
                toast.info("Đã bỏ yêu thích tuyến đường", { autoClose: 5000 });
            } else {
                await authApis().post(endpoints.add_favorite_route, null, { params: { routeId } });
                setFavoriteRouteIds(prev => [...prev, routeId]);
                toast.success("Đã thêm vào yêu thích", { autoClose: 5000 });
            }
        } catch (err) {
            const msg = err.response?.data || "Lỗi thao tác yêu thích.";
            toast.error(msg, { autoClose: 5000 });
        }
    };

    const handleReportSubmit = async () => {
        const formData = new FormData();
        formData.append("location", reportData.location);
        formData.append("description", reportData.description);
        if (reportData.image) formData.append("image", reportData.image);

        setReportLoading(true);
        try {
            await authApis().post(endpoints.report_traffic, formData);
            toast.success("Đã gửi báo cáo", { autoClose: 5000 });
            setShowReportModal(false);
            setReportData({ location: '', description: '', image: null });
        } catch (err) {
            toast.error("Lỗi khi gửi báo cáo", { autoClose: 5000 });
        } finally {
            setReportLoading(false);
        }
    };

    const center = selectedRoute?.stations?.[0] ? [selectedRoute.stations[0].latitude, selectedRoute.stations[0].longitude] : [10.7769, 106.7009];

    return (
        <div className="results-section position-relative">
            {reportLoading && (
                <div className="position-fixed top-0 start-0 w-100 h-100 bg-dark bg-opacity-50 d-flex justify-content-center align-items-center" style={{ zIndex: 9999 }}>
                    <Spinner animation="border" variant="light" className="me-3" />
                    <span className="text-white fs-5">Đang gửi báo cáo...</span>
                </div>
            )}

            <div className="results-banner">
                <h2 className="text-white fw-bold text-center py-4">Danh sách các chuyến đi</h2>
            </div>

            <ToastContainer position="top-right" />

            <Container className="my-5">
                {routes.length > 0 ? (
                    <Row className="g-4">
                        {routes.map((route, idx) => (
                            <Col key={idx} xl={4} lg={6} md={12}>
                                <Card className="route-card shadow rounded-4 p-3">
                                    <Card.Body>
                                        <div className="d-flex justify-content-between align-items-center mb-2">
                                            <div className="d-flex align-items-center">
                                                <MdDirectionsBus className="text-primary me-2" size={24} />
                                                <h5 className="mb-0 fw-semibold">{route.routeName}</h5>
                                            </div>
                                            <div className="d-flex align-items-center gap-2">
                                                <MdReport
                                                    size={20}
                                                    className="text-danger cursor-pointer"
                                                    title="Báo cáo"
                                                    onClick={() => {
                                                        setShowReportModal(true);
                                                        setReportData({ location: route.routeName, description: '', image: null });
                                                    }}
                                                />
                                                {favoriteRouteIds.includes(route.routeId) ? (
                                                    <MdFavorite
                                                        size={22}
                                                        className="text-danger cursor-pointer"
                                                        title="Bỏ yêu thích"
                                                        onClick={() => handleToggleFavorite(route.routeId)}
                                                    />
                                                ) : (
                                                    <MdFavoriteBorder
                                                        size={22}
                                                        className="text-danger cursor-pointer"
                                                        title="Thêm vào yêu thích"
                                                        onClick={() => handleToggleFavorite(route.routeId)}
                                                    />
                                                )}
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

                                        <Button variant="primary" className="rounded-pill w-100 mt-3" onClick={() => openDetail(route)}>
                                            Xem chi tiết
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                ) : (
                    <div className="text-center py-5">
                        <h4>Không tìm thấy chuyến đi phù hợp.</h4>
                    </div>
                )}
            </Container>

            <Modal show={showModal} onHide={() => setShowModal(false)} size="lg" centered>
                <Modal.Header closeButton>
                    <Modal.Title>Chi tiết chuyến đi</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {selectedRoute && (
                        <div>
                            <h4 className="fw-bold mb-2">{selectedRoute.routeName}</h4>
                            <p className="mb-2"><strong>Phương tiện:</strong> {selectedRoute.transportType}</p>

                            <h6 className="fw-semibold">Lịch trình:</h6>
                            <ul className="mb-3">
                                {selectedRoute.schedules.map((s, i) => (
                                    <li key={i}>
                                        <MdToday className="me-1" /> {formatDate(s.day)} |
                                        <MdAccessTime className="me-1" /> {formatTime(s.startTime)} - {formatTime(s.endTime)}
                                    </li>
                                ))}
                            </ul>

                            <h6 className="fw-semibold">Trạm dừng:</h6>
                            <ul>
                                {selectedRoute.stations.map((s, i) => (
                                    <li key={i}>
                                        <strong>{s.stationName}</strong> ({s.latitude}, {s.longitude})
                                    </li>
                                ))}
                            </ul>

                            <div className="mt-3" style={{ height: "300px", width: "100%" }}>
                                <MapContainer center={center} zoom={14} scrollWheelZoom={false} style={{ height: "100%", width: "100%" }}>
                                    <TileLayer
                                        attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors'
                                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                    />
                                    {selectedRoute.stations.map((s, i) => (
                                        <Marker key={i} position={[s.latitude, s.longitude]}>
                                            <Popup>{s.stationName}</Popup>
                                        </Marker>
                                    ))}
                                    <Polyline positions={selectedRoute.stations.map(s => [s.latitude, s.longitude])} color="blue" />
                                </MapContainer>
                            </div>
                        </div>
                    )}
                </Modal.Body>
            </Modal>

            <Modal show={showReportModal} onHide={() => setShowReportModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Báo cáo tuyến đường</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Vị trí</Form.Label>
                        <Form.Control
                            type="text"
                            value={reportData.location}
                            onChange={(e) => setReportData({ ...reportData, location: e.target.value })}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Mô tả</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={reportData.description}
                            onChange={(e) => setReportData({ ...reportData, description: e.target.value })}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Hình ảnh</Form.Label>
                        <Form.Control
                            type="file"
                            accept="image/*"
                            onChange={(e) => setReportData({ ...reportData, image: e.target.files[0] })}
                        />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowReportModal(false)}>Hủy</Button>
                    <Button variant="danger" onClick={handleReportSubmit} disabled={reportLoading}>
                        {reportLoading ? (<><Spinner animation="border" size="sm" className="me-2" /> Đang gửi...</>) : "Gửi báo cáo"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default RouteResults;
