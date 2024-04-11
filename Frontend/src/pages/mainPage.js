import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import OrderClient from "../api/orderClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class MainPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        this.client = new OrderClient();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */

const main = async () => {
    const mainPage = new MainPage();
    mainPage.mount();

    // Query elements inside main function after DOMContentLoaded
    let shoppingCart = document.querySelector(".shopping-cart");
    let searchForm = document.querySelector(".search-form");
    let loginForm = document.querySelector(".login-form");
    let navbar = document.querySelector(".navbar");

    window.onscroll = () => {
        shoppingCart.classList.remove("active");
        searchForm.classList.remove("active");
        loginForm.classList.remove("active");
        navbar.classList.remove("active");
    };

    document.querySelector("#search-btn").onclick = () => {
        searchForm.classList.toggle("active");
        shoppingCart.classList.remove("active");
        loginForm.classList.remove("active");
        navbar.classList.remove("active");
    };

    document.querySelector("#cart-btn").onclick = () => {
        shoppingCart.classList.toggle("active");
        searchForm.classList.remove("active");
        loginForm.classList.remove("active");
        navbar.classList.remove("active");
    };

    document.querySelector("#login-btn").onclick = () => {
        loginForm.classList.toggle("active");
        searchForm.classList.remove("active");
        shoppingCart.classList.remove("active");
        navbar.classList.remove("active");
    };

    document.querySelector("#menu-btn").onclick = () => {
        navbar.classList.toggle("active");
        loginForm.classList.remove("active");
        searchForm.classList.remove("active");
        shoppingCart.classList.remove("active");
    };
};

window.addEventListener('DOMContentLoaded', main);