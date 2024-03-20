import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderExample', 'showAddToCart'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {

        this.client = new ExampleClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const example = this.dataStore.get("example");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div>
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------




    showAddToCart() {
        const addToCartSection = document.getElementById("addToCartSection");
        if (addToCartSection) {
            addToCartSection.style.display = "block";
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const examplePage = new ExamplePage();
    examplePage.mount();

    // Adding event listener to the "Get Started" button
    const getStartedButton = document.getElementById('getStartedButton');
    if (getStartedButton) {
        getStartedButton.addEventListener('click', () => {
            examplePage.showAddToCart();
        });
    }

    // Adding event listener to the "Submit" button in the "Add Items to Cart" section
    const addToCartButton = document.getElementById('addToCartButton');
    if (addToCartButton) {
        addToCartButton.addEventListener('click', () => {
            // Call the function to handle submission
        });
    }
};

window.addEventListener('DOMContentLoaded', main);
