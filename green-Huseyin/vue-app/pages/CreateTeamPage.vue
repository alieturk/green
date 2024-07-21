<template>
  <!-- Alert if success-->
  <div v-if="alert === true" class=" text-center py-4 lg:px-4">
    <div class="p-2 bg-emerald-400 items-center text-white leading-none lg:rounded-full flex lg:inline-flex"
         role="alert">
      <span class="font-semibold mr-2 text-left flex-auto">Your team has been created. You are being redirected to the dashboard<svg
          aria-hidden="true"
          class=" inline-block ml-2 mr-2 w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-emerald-600"
          viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
            d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
            fill="currentColor"/>
        <path
            d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
            fill="currentFill"/>
    </svg>
    <span class="sr-only">Loading...</span></span>
      <svg class="fill-current opacity-75 h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
        <path d="M12.95 10.707l.707-.707L8 4.343 6.586 5.757 10.828 10l-4.242 4.243L8 15.657l4.95-4.95z"/>
      </svg>
    </div>
  </div>
  <!-- Alert if not success-->
  <div v-if="sentAlert === true" class=" text-center py-4 lg:px-4">
    <div class="p-4 bg-red-600 items-center text-white leading-none lg:rounded-full flex lg:inline-flex" role="alert">
      <span class="font-semibold mr-2 text-left flex-auto">You have already a team you assigned to</span>
      <svg class="fill-current opacity-75 h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
        <path d="M12.95 10.707l.707-.707L8 4.343 6.586 5.757 10.828 10l-4.242 4.243L8 15.657l4.95-4.95z"/>
      </svg>
    </div>
  </div>
  <!--Body-->
  <div class="container my-24 px-6 mx-auto">
    <section class="mb-32 text-gray-800 text-center lg:text-left">
      <div class="px-6 py-12 lg:my-12">
        <div class="container mx-auto xl:px-32">
          <div class="grid lg:grid-cols-2 gap-12 flex items-center">
            <div class="form-i mt-12 lg:mt-0 self-start">
              <h1 class="text-4xl md:text-5xl xl:text-6xl font-bold tracking-tight mb-12">
                Create your Team<br/><span class="text-emerald-800">to achieve more</span>
              </h1>
              <div class="form-p md:flex mb-4">
                <input
                    type="text"
                    class="form-t block px-4 mb-2 py-2 md:mb-0 md:mr-2 w-full text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                    id="input-name"
                    placeholder="Enter the name of your team"
                    v-model.trim="name"
                />
              </div>
              <div class="form-g md:flex flex-row" :class="{invalid : active === 'invalid'}">
                <div class="relative w-full">
                  <input
                      type="email"
                      class="form-control block w-full px-4 py-2 mb-2 md:mb-0 md:mr-2 text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                      id="input-mail"
                      placeholder="Enter the e-mail address of someone you know"
                      v-model.trim="mail"
                      @blur="validateInput"
                  />
                  <div class="absolute top-1 right-2">
                    <button class="h-15 w-15 bg-inherit justify-center" @click="addMail">
                      <svg style="color: rgb(192, 192, 192);" xmlns="http://www.w3.org/2000/svg" width="40" height="40"
                           fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                        <path
                            d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"
                            fill="#c0c0c0"></path>
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
              <div>
                <p v-if="active === 'invalid'" class="md:font-bold text-red-800">Please enter a valid e-mail address</p>
                <p v-else-if="active === 'empty'" class="md:font-bold text-red-800">Please provide an e-mail address</p>
              </div>
              <div class="md:flex">
                <ul class="inline-block bg-white rounded-lg w-full text-gray-900">
                  <li class="flex px-6 py-2 border-b border-gray-200 rounded-t-lg justify-center"
                      v-for="(mail, index) in emails" :key="mail">{{ mail }}
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                         stroke="currentColor" class="svg w-5 h-5 inline-block" @click="deleteMail(index)">
                      <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                  </li>
                  <button v-if="emails.length >= 1"
                          @click.prevent="submitList"
                          type="submit"
                          class="w-full inline-block px-6 py-2 border-2 border-emerald-800 text-emerald-800
                          font-medium text-xs leading-normal uppercase rounded hover:bg-black hover:bg-opacity-5
                           focus:outline-none focus:ring-0 transition duration-150 ease-in-out mt-3">Confirm
                  </button>
                </ul>
              </div>
              <button v-if="emails.length < 1" @click="submitList" class="text-emerald-800  ">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                     stroke="currentColor" class="w-3 h-3 inline-block ">
                  <path stroke-linecap="round" stroke-linejoin="round"
                        d="M11.25 4.5l7.5 7.5-7.5 7.5m-6-15l7.5 7.5-7.5 7.5"/>
                </svg>
                You can also start by yourself and add your teammates later on
              </button>
            </div>
            <div class="mb-12 lg:mb-0 self-start">
              <div class="embed-responsive embed-responsive-16by9 relative w-full overflow-hidden rounded-lg shadow-lg"
                   style="padding-top: 56.25%;">
                <iframe
                    class="embed-responsive-item absolute top-0 right-0 bottom-0 left-0 w-full h-full"
                    width="560" height="315"
                    src="https://www.youtube.com/embed/M-iJM02m_Hg"
                    allowfullscreen=""
                    data-gtm-yt-inspected-2340190_699="true"
                    id="240632615"
                ></iframe>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
  <router-view></router-view>
</template>

<script>

export default {
  inject: ["groupService"],
  name: "createTeamPage",
  /**
   *
   * @returns {{emails: *[], mail: string, active: string}}
   */
  data() {
    return {
      name: '',
      active: '',
      mail: '',
      emails: [],
      alert: false,
      sentAlert: false,
      userId: this.$store.getters.user,
    }
  },
  /**
   * If the entered mail address not empty and correct is, it will be added to the list as an item.
   */
  methods: {
    addMail: function () {
      if (this.active === "valid" && this.mail !== '') {
        this.emails.push(this.mail);
        this.mail = ''
      }
    },
    /**
     * The method will delete the unintentionally added emails.
     * @param index
     */
    deleteMail: function (index) {
      this.emails.splice(index, 1);
    },
    /**
     * The method validates if input has a value of an email
     */
    validateInput() {
      if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(this.mail)) {
        this.active = "valid"
      } else if (this.mail === '') {
        this.active = 'empty'
      } else
        this.active = "invalid";
    },
    /**
     * @returns {Promise<void>}
     */
    async submitList() {
      let name = this.name
      let data = {name}
      await this.groupService.asyncPost(data, this.$store.getters.user.emailAddress, this.emails)
          .then((response) => {
            if (response) {

              let user = this.$store.getters.user;
              localStorage.setItem("User", user);

              user.group = response
              this.$store.commit('setUser', user);
              this.alert = true;
              this.emails = [];
              this.name = '';
              setTimeout(() => {
                this.$router.push("/dashboard")
              }, 3500)
            } else {
              this.sentAlert = true;
              this.emails = [];
              this.name = '';
            }
          })
    }
  }
}
</script>

<style scoped>
.form-g.invalid input {
  border-color: red;
}

.svg {
  margin-left: auto;
}
</style>
