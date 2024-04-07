import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the ExampleService.
 */
export default class OrderClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'startOrder', 'addItemToOrder', 'searchOrderByName', 'findAll', 'removeItemFromOrder'];
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

    async startOrder(request, errorCallback) {
        try {
            const response = await this.client.post('/order/startOrder',
                {
                    userName: request,
                    items: []
                });
            return response.data;
        } catch (error) {
            this.handleError("startOrder", error, errorCallback);
        }
    }

    async addItemToOrder(username, item, errorCallback) {
        try {
            const response = await this.client.post(`/order/add-item/${username}`,[item]);
            return response.data;
        } catch (error) {
            this.handleError("addItemToOrder", error, errorCallback);
        }
    }

    async searchOrderByName(username, errorCallback) {
        try {
            const response = await this.client.get(`/order/search/${username}`);
            return response.data;
        } catch (error) {
            this.handleError("searchOrderByName", error, errorCallback);
        }
    }

    async findAll(errorCallback) {
        try {
            const response = await this.client.get(`/order/all`);
            return response.data;
        } catch (error) {
            this.handleError("findAll", error, errorCallback);
        }
    }

    async removeItemFromOrder(username, item, errorCallback) {
        try {
            const response = await this.client.delete(`/order/remove-item/${username}?item=${item}`);
            return response.data;
        } catch (error) {
            this.handleError("removeItemFromOrder", error, errorCallback);
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