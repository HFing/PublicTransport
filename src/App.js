import './App.css';
import React, { useReducer } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import RouteResults from "./components/RouteResults";
import Register from "./components/Register";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import FavoriteRoutesPage from "./components/FavoriteRoutesPage";
import Login from "./components/Login";
import "bootstrap/dist/css/bootstrap.min.css";
import 'leaflet/dist/leaflet.css';

import MyUserReducer from "./reducers/MyUserReducer";
import { MyDispatcherContext, MyUserContext } from "./configs/MyContexts";

import { GoogleOAuthProvider } from '@react-oauth/google';

function App() {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <GoogleOAuthProvider clientId="212682045650-3lauovlo5kqcn92lutk0rch06s4mesni.apps.googleusercontent.com">
      <MyUserContext.Provider value={user}>
        <MyDispatcherContext.Provider value={dispatch}>
          <BrowserRouter>
            <Header />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/routes" element={<RouteResults />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/profile" element={<FavoriteRoutesPage />} />
            </Routes>
            <Footer />
          </BrowserRouter>
        </MyDispatcherContext.Provider>
      </MyUserContext.Provider>
    </GoogleOAuthProvider>
  );
}


export default App;
