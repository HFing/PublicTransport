import React, { useState, useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Container, Row, Col, Card, Button, Modal, Badge } from "react-bootstrap";
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
    const user = useContext(MyUserContext);

    // Lấy danh sách yêu thích
    useEffect(() => {
        const fetchFavorites = async () => {
            try {
                const res = await authApis().get(endpoints.get_favorite_routes);
                const ids = res.data.map(item => item.routeId);
                console.log("Danh sách yêu thích:", ids);
                setFavoriteRouteIds(ids);
            } catch (err) {
                console.error("Lỗi khi lấy danh sách yêu thích:", err);
            }
        };
        fetchFavorites();
    }, []);

    const formatTime = (t) => t?.slice(0, 5);
    const formatDate = (dateStr) => {
        const d = new Date(dateStr);
        return d.toLocaleDateString("vi-VN", {
            weekday: "long", year: "numeric", month: "long", day: "numeric"
        });
    };

    const openDetail = (route) => {
        setSelectedRoute(route);
        setShowModal(true);
    };

    const handleAddFavorite = async (routeId) => {
        try {
            await authApis().post(endpoints.add_favorite_route, null, {
                params: { routeId }
            });
            alert("Đã thêm vào yêu thích!");
            setFavoriteRouteIds(prev => [...prev, routeId]); // cập nhật UI
        } catch (err) {
            const msg = err.response?.data || "Thêm vào yêu thích thất bại.";
            alert(msg);
        }
    };

    const center = selectedRoute?.stations?.[0]
        ? [selectedRoute.stations[0].latitude, selectedRoute.stations[0].longitude]
        : [10.7769, 106.7009];

    return (
        <div className="results-section">
            <div className="results-banner">
                <h2 className="text-white fw-bold text-center py-4">Danh sách các chuyến đi</h2>
            </div>

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
                                                <MdReport size={20} className="text-danger cursor-pointer" />
                                                {favoriteRouteIds.includes(route.routeId) ? (
                                                    <MdFavorite size={22} className="text-danger" title="Đã yêu thích" />
                                                ) : (
                                                    <MdFavoriteBorder
                                                        size={22}
                                                        className="text-danger cursor-pointer"
                                                        title="Thêm vào yêu thích"
                                                        onClick={() => handleAddFavorite(route.routeId)}
                                                    />
                                                )}
                                            </div>
                                        </div>

                                        <p className="text-muted mb-2">
                                            <MdOutlineArrowForward className="me-1" />
                                            Phương tiện: <strong>{route.transportType}</strong>
                                        </p>

                                        {route.schedules && route.schedules.length > 0 && (
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

                                        <Button
                                            variant="primary"
                                            className="rounded-pill w-100 mt-3"
                                            onClick={() => openDetail(route)}
                                        >
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
                                    <Polyline
                                        positions={selectedRoute.stations.map(s => [s.latitude, s.longitude])}
                                        color="blue"
                                    />
                                </MapContainer>
                            </div>
                        </div>
                    )}
                </Modal.Body>
            </Modal>
        </div>
    );
};

export default RouteResults;
