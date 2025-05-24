import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/api/';

export const endpoints = {
    search_routes: "routes/search",
    search_stations: "stations/search",
    register: "users",
    login: "login",
    current_user: '/secure/profile',
    add_favorite_route: "/secure/favorite-routes/add",
    remove_favorite_route: "/secure/favorite-routes/remove",
    get_favorite_routes: "/secure/favorite-routes",

};

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});
