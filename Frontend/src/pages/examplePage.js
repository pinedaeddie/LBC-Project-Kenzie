import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ExamplePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderExample', 'showAddToCart', 'populateItemsBySection'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {

        this.client = new ExampleClient();

        this.dataStore.addChangeListener(this.renderExample)

        // Adding event listener for section change
        const sectionDropdown = document.getElementById('Section');
        if (sectionDropdown) {
            sectionDropdown.addEventListener('change', this.populateItemsBySection.bind(this));
        }
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


    populateItemsBySection() {
        var sectionDropdown = document.getElementById('Section');
        var section = sectionDropdown.value;
        var itemSelectionDiv = document.getElementById('itemSelection');
        if (section === 'Select') {
            itemSelectionDiv.innerHTML = '';
        } else {
            var options = {
                'Veggies': ['Tomato', 'Spinach', 'Onion', 'Parsley', 'Potatoes', 'Cabbage'],
                'Fruits': ['Apple', 'Banana', 'Orange', 'Blackberry', 'Strawberry'],
                'Dairy': ['Eggs', 'Milk', 'Yogurt', 'Cheese'],
                'Beverages': ['Coke','Water', 'Sprite', 'Red Bull', 'Lemonade'],
            };

            var itemsHTML = '';
            options[section].forEach(function(item) {
                itemsHTML += '<option value="' + item + '">' + item + '</option>';
            });

            itemSelectionDiv.innerHTML = '<label for="Item">Item:</label><select id="Item" name="Item" required>' +
            '<option value="" disabled selected>Select</option>' +
            itemsHTML +
            '</select>';
        }
    }


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

    // Hiding the submit button initially
    const submitButton = document.getElementById('addToCartButton');
    if (submitButton) {
        submitButton.style.display = 'none';
    }

    // Adding event listener to the "Get Started" button
    const getStartedButton = document.getElementById('getStartedButton');
    if (getStartedButton) {
        getStartedButton.addEventListener('click', () => {
            examplePage.showAddToCart();

            // Showing the "Section" field
            const sectionField = document.getElementById('Section');
            if (sectionField) {
                sectionField.style.display = 'block';
            }

            // Hiding the "Quantity" text
            const quantityLabel = document.querySelector('label[for="Quantity"]');
            if (quantityLabel) {
                quantityLabel.style.display = 'none';
            }

            // Hiding the "Quantity" select box
            const quantitySelect = document.getElementById('Quantity');
            if (quantitySelect) {
                quantitySelect.style.display = 'none';
            }
        });
    }

    // Adding event listener to the "Section" dropdown
    const sectionDropdown = document.getElementById('Section');
    if (sectionDropdown) {
        sectionDropdown.addEventListener('change', () => {
            const selectedItem = sectionDropdown.value;
            const itemSelectionDiv = document.getElementById('itemSelection');
            const quantityLabel = document.querySelector('label[for="Quantity"]');
            const quantitySelect = document.getElementById('Quantity');

            // Showing the "Item" and "Quantity" fields when a section is selected
            if (selectedItem !== 'Select') {
                itemSelectionDiv.style.display = 'block';
                quantityLabel.style.display = 'block';
                quantitySelect.style.display = 'block';
            } else {
                // Hiding the "Item" and "Quantity" fields if the default option is selected
                itemSelectionDiv.style.display = 'none';
                quantityLabel.style.display = 'none';
                quantitySelect.style.display = 'none';
            }
        });
    }

    // Adding event listener to the "Quantity" dropdown
    const quantitySelect = document.getElementById('Quantity');
    if (quantitySelect) {
        quantitySelect.addEventListener('change', () => {
            // Showing the submit button when both section and quantity are selected
            if (sectionDropdown.value !== 'Select' && quantitySelect.value !== '') {
                submitButton.style.display = 'block';
            } else {
                submitButton.style.display = 'none';
            }
        });
    }

    // Adding event listener to the "Submit" button in the "Add Items to Cart" section
    const addToCartButton = document.getElementById('addToCartButton');
    if (addToCartButton) {
        addToCartButton.addEventListener('click', () => {
            // Retrieving selected values
            const section = document.getElementById('Section').value;
            const item = document.getElementById('Item').value;
            const quantity = document.getElementById('Quantity').value;

            // Creating HTML to display the selected items
            const selectedItemHTML = `
                <div>
                    <p>Section: ${section}</p>
                    <p>Item: ${item}</p>
                    <p>Quantity: ${quantity}</p>
                </div>
            `;

            const selectedItemsContainer = document.getElementById('selectedItemsContainer');
            if (selectedItemsContainer) {
                selectedItemsContainer.innerHTML = '';
                selectedItemsContainer.insertAdjacentHTML('beforeend', selectedItemHTML);
                selectedItemsContainer.style.display = 'block';
            }
        });
    }
};

window.addEventListener('DOMContentLoaded', main);
