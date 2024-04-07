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


    // Event Handlers --------------------------------------------------------------------------------------------------

}

/**
 * Main method to run when the page contents have loaded.
 */

const main = async () => {
    const mainPage = new MainPage();
    mainPage.mount();

};

window.addEventListener('DOMContentLoaded', main);