import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import OrderClient from "../api/orderClient";

class OrderHistoryPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onFindAll','onFindOrderByUserName','renderOrder'], this);
        this.dataStore = new DataStore();
    }

    async mount() {

        document.getElementById('find-order-by-name').addEventListener('submit', this.onFindOrderByUserName);
        document.getElementById('findAll').addEventListener('submit', this.onFindAll);

        this.client = new OrderClient();
        this.dataStore.addChangeListener(this.renderOrder)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderOrder() {
        event.preventDefault;
        let result = document.getElementById("result-info");

        const orderRecord = this.dataStore.get("orderRecord");
        const listOfRecords = this.dataStore.get("orderList");

        if (orderRecord) {
            const id = orderRecord.id;
            const userName = orderRecord.userName;
            const orderDate = orderRecord.orderDate;
            const items = orderRecord.items;

            let userNameHtml = `<h3> UserName: </h3> <p>${userName}</p>`;
            let idHtml = `<h3> ID: </h3><p>${id}</p>`;
            let orderDateHtml = `<h3> Order Date: </h3><p>${orderDate}</p>`;
            let html = `<h3> Items: </h3>`;
            for(let i = 0; i < items.length; i++){
                html += `<p>${items[i]}</p>`;
            }
            result.innerHTML = userNameHtml + idHtml + orderDateHtml + html;
        } else if(listOfRecords){
            let list = "";
            for(let i = 0; i < listOfRecords.length; i++){
                const id = listOfRecords[i].id;
                const userName = listOfRecords[i].userName;
                const orderDate = listOfRecords[i].orderDate;
                const items = listOfRecords[i].items;
                let userNameHtml = `<h3> UserName: </h3> <p>${userName}</p>`;
                let idHtml = `<h3> ID: </h3><p>${id}</p>`;
                let orderDateHtml = `<h3> Order Date: </h3><p>${orderDate}</p>`;
                let html = `<h3> Items: </h3>`;
                for(let i = 0; i < items.length; i++){
                    html += `<p>${items[i]}</p>`;
                }
                html += `<br>`;

                list += userNameHtml + idHtml + orderDateHtml + html;
            }
            result.innerHTML = list;
        } else {
            result.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onFindOrderByUserName (event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let userName = document.getElementById("find-userName").value;

        try {
            if (userName) {
                const orderRecord = await this.client.searchOrderByName(userName);
                this.dataStore.set("orderRecord", orderRecord);
                this.showMessage(`Order found: ${orderRecord.id}`);
            } else {
                throw new Error("Invalid input. Please provide an username.");
            }
        } catch (error) {
            this.errorHandler("Error retrieving order. Please try again.");
        }
    }


    async onFindAll (event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let orderList = await this.client.findAll();
        this.dataStore.set("orderList", orderList);

        if (result) {
            this.showMessage(`Orders retrieved successfully!`);
        } else {
            this.errorHandler(`Error retrieving orders. Please try again...`);
        }
    }
}

const main = async () => {
    const orderHistoryPage = new OrderHistoryPage;
    orderHistoryPage.mount();
};

window.addEventListener('DOMContentLoaded', main);