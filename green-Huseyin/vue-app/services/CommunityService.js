import store from "@/store/modules";

/**
 * This skeleton-type class helps with transferring data between front-end and back-end for community activities
 */
export class CommunityService {
    resourcesURL;
    #authenticationToken;

    constructor() {
        this.resourcesURL = process.env.VUE_APP_API_URL;
        this.#authenticationToken = store.getters.token;

    }

    /**
     * With this method below, we send the data required for the post to the backend.
     * @param file image
     * @param title Title of post
     * @param text  text of post
     * @returns {Promise<Response>}
     */
    async asyncPostPost(file, title, text){
        //Since we are sending a file, we are sending it as form data instead of json.
        let formData = new FormData();
        formData.set('file', file)
        formData.set('content',new Blob([JSON.stringify({
            title: title.toString(),
            text: text.toString()
        })], {
            type: "application/json"
        }))
        return fetch(this.resourcesURL + "/community/postPosts",
            {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + store.getters.token
                },
                body: formData
            }).catch((error) => {
                console.log(error)
        });
    }

    /**
     * @returns all posts
     */
    async getAllPosts(){
        const data = await fetch(this.resourcesURL + "/community/posts",
            {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': 'Bearer ' + store.getters.token,

                },
            })
        return data.json()
    }
}