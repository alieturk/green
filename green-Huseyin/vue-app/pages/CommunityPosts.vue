<template>
  <!-- Close Icon -->
  <link
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
      rel="stylesheet"/>
  <!-- Alert -->
  <div v-if="alert === true" class="text-center py-4 lg:px-4">
    <div
        class="alertbox py-5 px-2 bg-emerald-400 items-center text-white leading-none lg:rounded-full flex lg:inline-flex"
        role="alert">
      <span class="font-semibold mr-2 text-left flex-auto">Your post is succesfully created</span>
    </div>
  </div>
  <!-- Main body -->
  <!-- Header -->
  <section class=" py-1 bg-blueGray-50">
    <div class="w-full lg:w-8/12 px-4 mx-auto mt-6">
      <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0">
        <div class="rounded-t bg-white mb-0 px-6 py-6">
          <div class="text-center flex justify-between">
            <h6 class="text-blueGray-700 text-xl font-bold">
              Create Post
            </h6>
          </div>
        </div>
        <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
          <form enctype="multipart/form-data" @submit.prevent="submitForm">
            <h6 class="text-blueGray-400 text-sm mt-3 mb-6 font-bold uppercase">
              Post Information
            </h6>
            <!-- Form -->
            <div class="flex flex-wrap">
              <div class="w-full lg:w-12/12 px-4">
                <div class="relative w-full mb-3">
                  <label class="block uppercase text-blueGray-600 text-xs font-bold mb-2">
                    Title
                  </label>
                  <input v-model="title"
                         class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                         placeholder="Title" type="text">
                </div>
              </div>
              <div class="w-full lg:w-12/12 px-4">
                <div class="relative w-full mb-3">
                  <label class="block uppercase text-blueGray-600 text-xs font-bold mb-2">
                    More details
                  </label>
                  <textarea v-model="text"
                            class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                            placeholder="Details"
                            rows="4" type="text"> Info</textarea>
                </div>
              </div>
              <div class="w-full lg:w-12/12 px-4">
                <div class="relative w-full mb-3">
                  <label class="block uppercase text-blueGray-600 text-xs font-bold mb-2" for="formFile">
                    Add image
                  </label>
                  <label
                      class="imageLabel border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring ease-linear transition-all duration-150"
                      for="formFile">
                    Select Image
                  </label>
                  <input id="formFile" accept="image/*" class="imageInput"
                         type="file"
                         @change="onChange">
                    <p id="uploadedFile">{{ this.uploadedFileName }}</p>
                    <button type="button" @click="onRemove()" class="removeImage"><span class="material-symbols-outlined">close</span></button>
                </div>
                <!-- Close Icon -->
                <button
                    class="hover:shadow-form w-full rounded-md bg-emerald-800 py-3 px-8 text-center text-base font-semibold text-white outline-none"
                    type="submit">
                  Post
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  inject: ["communityService"],
  name: "CommunityPosts",
  data() {
    return {
      title: '',
      text: '',
      file: null,
      alert: false,
      uploadedFileName: 'Waiting for file...'
    }
  },

  methods: {
    //The submit method allows us to call the fetch method and call the API.
    async submitForm() {
      await this.communityService.asyncPostPost(this.file, this.title, this.text)
          .then((response) => {
            if (response) {
              this.alert = true;
              this.title = '';
              this.text = '';
              this.file = null;
            }
          })
    },
    onChange(event) {
      this.file = event.target.files[0];
      this.uploadedFileName = this.file.name;
    },
    //helps with removing unwanted added images
    onRemove() {
      this.file = null;
      this.uploadedFileName = 'Waiting for file...';
    }
  }
}
</script>

<style scoped>
.imageInput {
  height: 0.1px;
  opacity: 0;
  overflow: hidden;
  position: absolute;
  z-index: -1;
}

.imageLabel {
  padding: 10px;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  margin-bottom: 20px;
}

.alertbox {
  align-items: center;
  padding: 20px;
  border-radius: 10px;
  background: #065e46;
  font-size: 16px;
  font-weight: 500;
}

#uploadedFile {
  display: inline;
  margin-left: 10px;
}

.removeImage {
  position: relative;
top: 5px;
  left: 10px;
}
</style>
