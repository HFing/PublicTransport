import './App.css';
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import RouteResults from "./components/RouteResults";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import "bootstrap/dist/css/bootstrap.min.css";
import 'leaflet/dist/leaflet.css';


function App() {
  return (
    <BrowserRouter>
      <Header />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/routes" element={<RouteResults />} />
      </Routes>

      <Footer />
    </BrowserRouter>
  );
}

export default App;
