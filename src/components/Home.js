import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Row, Col, Button } from "react-bootstrap";
import { MdLocationOn, MdDateRange, MdSwapHoriz } from "react-icons/md";
import Header from "./layouts/Header"; // Giữ lại nếu cần riêng
import { Typeahead } from "react-bootstrap-typeahead";
import "react-bootstrap-typeahead/css/Typeahead.css";
import API, { endpoints } from "../configs/Apis";
import "./css/Home.css";

function Home() {
    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");
    const [date, setDate] = useState("");
    const [stationSuggestions, setStationSuggestions] = useState([]);
    const navigate = useNavigate();

    const handleSearch = async () => {
        if (!from || !to || !date) {
            alert("Vui lòng nhập đầy đủ thông tin Nơi đi, Nơi đến và Ngày.");
            return;
        }

        try {
            const res = await API.get(endpoints["search_routes"], {
                params: { from, to, date }
            });
            navigate("/routes", { state: { routes: res.data } });
        } catch (err) {
            console.error("Lỗi tìm kiếm:", err);
        }
    };

    const fetchStations = async (query) => {
        if (query.length < 3) {
            setStationSuggestions([]);
            return;
        }

        try {
            const res = await API.get(endpoints["search_stations"], {
                params: { keyword: query }
            });
            setStationSuggestions(res.data.map(s => s.stationName));
        } catch (err) {
            console.error("Lỗi gợi ý trạm:", err);
        }
    };

    return (
        <>
            <Header />
            <div className="hero-section">
                <div className="search-container">
                    <Row className="g-3 align-items-center justify-content-center">
                        <Col xl={3} lg={3} md={4} sm={12}>
                            <div className="input-wrapper">
                                <MdLocationOn className="me-2 text-primary" size={20} />
                                <Typeahead
                                    id="from-input"
                                    onInputChange={(text) => fetchStations(text)}
                                    onChange={(selected) => setFrom(selected[0] || "")}
                                    options={stationSuggestions}
                                    placeholder="Nơi đi"
                                    selected={from ? [from] : []}
                                    minLength={3}
                                    inputProps={{ className: "no-border-input" }}
                                    onBlur={() => setStationSuggestions([])}
                                />
                            </div>
                        </Col>

                        <Col xl={1} lg={1} md={1} sm={12} className="text-center">
                            <MdSwapHoriz size={24} className="text-secondary" />
                        </Col>

                        <Col xl={3} lg={3} md={4} sm={12}>
                            <div className="input-wrapper">
                                <MdLocationOn className="me-2 text-danger" size={20} />
                                <Typeahead
                                    id="to-input"
                                    onInputChange={(text) => fetchStations(text)}
                                    onChange={(selected) => setTo(selected[0] || "")}
                                    options={stationSuggestions}
                                    placeholder="Nơi đến"
                                    selected={to ? [to] : []}
                                    minLength={3}
                                    inputProps={{ className: "no-border-input" }}
                                    onBlur={() => setStationSuggestions([])}
                                />
                            </div>
                        </Col>

                        <Col xl={3} lg={3} md={4} sm={12}>
                            <div className="input-wrapper">
                                <MdDateRange className="me-2 text-info" size={20} />
                                <input
                                    type="date"
                                    value={date}
                                    onChange={(e) => setDate(e.target.value)}
                                    className="form-control border-0 shadow-none no-border-input"
                                />
                            </div>
                        </Col>

                        <Col xl={2} lg={2} md={12} sm={12} className="text-end mt-2">
                            <Button
                                onClick={handleSearch}
                                className="w-100 py-2 rounded-pill fw-bold search-btn"
                            >
                                Tìm kiếm
                            </Button>
                        </Col>
                    </Row>
                </div>
            </div>
        </>
    );
}

export default Home;
