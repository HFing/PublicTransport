import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/';

export const endpoints = {
    search_routes: "routes/search",
    search_stations: "stations/search"
};

// Không cần Authorization
export default axios.create({
    baseURL: BASE_URL
});
