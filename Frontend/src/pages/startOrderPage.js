import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import OrderClient from "../api/orderClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class StartOrderPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onStartOrder'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('start-order').addEventListener('submit', this.onStartOrder);

        this.client = new OrderClient();
    }


    // Event Handlers --------------------------------------------------------------------------------------------------

    async onStartOrder(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let request = document.getElementById("create-userName-field").value;

        const orderStarted = await this.client.startOrder(request, this.errorCallback);
        this.dataStore.set("orderRecord", orderStarted);

        if (orderStarted) {
            this.showMessage(`Order successfully started!`);
        } else {
            this.errorHandler("Error creating order. Please try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */

const main = async () => {
    const startOrderPage = new StartOrderPage();
    startOrderPage.mount();

};

window.addEventListener('DOMContentLoaded', main);