import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import OrderClient from "../api/orderClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ShoppingCart extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderCurrentOrder', 'onAddItem', 'onRemoveItem'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('add-order').addEventListener('submit', this.onAddItem);
        document.getElementById('delete-order').addEventListener('submit', this.onRemoveItem);

        this.client = new OrderClient();
        this.dataStore.addChangeListener(this.renderCurrentOrder)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderCurrentOrder() {
        let result = document.getElementById("result-info");
        const example = this.dataStore.get("orderRecord");
        if (example) {
            const list = example.items;
            if (list && list.length > 0) {
                let html = "<li>";
                for (let i = 0; i < list.length; i++) {
                    html += `<h4>${list[i]}</h4>`;
                }
                html += "</li>";
                result.innerHTML = html;
            } else {
                result.innerHTML = "No Items";
            }
        } else {
            result.innerHTML = "No Order Record";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onAddItem(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let userName = document.getElementById("create-add-field").value;
        let itemToAdd = document.getElementById("item-list-field").value;

        const updatedOrder = await this.client.addItemToOrder(userName, itemToAdd, this.errorCallback);
        this.dataStore.set("orderRecord", updatedOrder);

        if (updatedOrder) {
            this.showMessage(`Added item ${itemToAdd} to order for ${userName}!`);
        } else {
            this.errorHandler("Error adding item from order! Please try again...");
        }
    }


    async onRemoveItem(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let userName = document.getElementById("create-name-field").value;
        let itemToRemove = document.getElementById("remove-item-field").value;

        const updatedOrder = await this.client.removeItemFromOrder(userName, itemToRemove, this.errorCallback);
        this.dataStore.set("orderRecord", updatedOrder);

        this.showMessage(`Removed item ${itemToRemove} from order for ${userName}!`);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */

const main = async () => {
    const shoppingCart = new ShoppingCart();
    shoppingCart.mount();

};

window.addEventListener('DOMContentLoaded', main);