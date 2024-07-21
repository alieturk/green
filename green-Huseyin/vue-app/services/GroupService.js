import store from "@/store/modules";

/**
 * This singleton-type class provide an interface to exchanging data between backend and frontend for group activities.
 */

export class GroupService {
    resourcesUrl;
    #authenticationToken;
    /**
     * The resourcesUrl in the constructor specifies the endpoint of the backend API.
     */
    constructor() {
        this.resourcesUrl = process.env.VUE_APP_API_URL;
        this.#authenticationToken = store.getters.token;
    }

    /**
     * The fetchJson issues all
     * AJAX requests to the backend API. POST, PUT and DELETE requests can be
     * configured by means of the options parameter
     * @param url
     * @param options
     * @returns {Promise<undefined|any>}
     */
    async fetchJson(url, options = null) {
        let response = await fetch(url, options)
        if (response.ok) {
            return await response.json();
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return undefined;
        }
    }

    /**
     * This method make API call to create a team with or without teammates
     * @param group It contains the name of the team
     * @param email The mail address of the user that creates the team
     * @param emails Mail of invited person(s)
     * @returns {Promise<any | undefined>}
     */
    async asyncPost(group, email, emails){
        //Casting to Json object
        const body = JSON.stringify({group: group, emails: emails});

        return this.fetchJson(this.resourcesUrl + "/" + "group/" + email,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + store.getters.token
                },
                body: body

            }).catch(err => err.message);
    }

    /**
     * Calling backend request to send invitation code
     * @param email The mail address of the user that invites people
     * @param emails mail addresses
     * @returns {Promise<* | undefined>}
     */
    async asyncPostMember(email, emails){
        //Casting to Json object
        const body = JSON.stringify({emails: emails});
        let response = this.fetchJson(this.resourcesUrl + `/group/${email}/sendInvites`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + store.getters.token
                },
                body: body
            }).catch(err => err.message);


        if (response.ok) {
            return await response.json().catch(err => err.message);
        } else {
            return response.status;
        }
    }

    /**
     *
     * @param code Invited people get code
     * @param email Email of the person who is invited
     * @returns {Promise<Response>}
     */
    async asyncPostCode(code, email){
        const body = JSON.stringify(code)
       let response = fetch(this.resourcesUrl + "/" + "group/" + email  + "/addMember",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + store.getters.token
                },
                body: body
            }).catch(err => err.message)

        if (response.ok) {
            return await response.json().catch(err => err.message);
        } else {
            return response.status;
        }
    }

    async getChatMessages(groupId) {
        return this.fetchJson(this.resourcesUrl + `/group/${groupId}/chatMessages`, {
            method: 'GET',
            headers: {
                'Authorization': store.getters.token
            }
        });
    }

}
