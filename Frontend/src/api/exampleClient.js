import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class ExampleClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['startOrder', 'addItemToOrder', 'getOrderDetail', 'findAllOrders', 'getOrderTotal', 'removeItemFromOrder', 'searchItemById'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async startOrder(orderRequest) {
        try {
            const response = await this.client.post('/order/start', orderRequest);
            return response.data;
        } catch (error) {
            this.handleError("starting order", error, errorCallback);
        }
    }

    async addItemToOrder(id, itemRequest) {
        try {
            const response = await this.client.post(`/order/add-item/${id}`, itemRequest);
            return response.data;
        } catch (error) {
            this.handleError("adding item to order", error, errorCallback);
        }
    }

    async getOrderDetail(orderId) {
        try {
            const response = await this.client.get(`/order/${orderId}`);
            return response.data;
        } catch (error) {
            this.handleError("getting order details", error, errorCallback);
        }
    }

    async findAllOrders() {
        try {
            const response = await this.client.get('/order/all');
            return response.data;
        } catch (error) {
            this.handleError("getting all orders", error, errorCallback);
        }
    }

    async getOrderTotal(orderId) {
        try {
            const response = await this.client.get(`/order/total/${orderId}`);
            return response.data;
        } catch (error) {
            this.handleError("getting order total", error, errorCallback);
        }
    }

    async removeItemFromOrder(itemId, orderId) {
        try {
            const response = await this.client.delete(`/order/remove-item/${itemId}/${orderId}`);
            return response.data;
        } catch (error) {
            this.handleError("removing item from order", error, errorCallback);
        }
    }

    async searchItemById(itemId) {
        try {
            const response = await this.client.get(`/order/items/search/${itemId}`);
            return response.data;
        } catch (error) {
            this.handleError("searchItemById", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
