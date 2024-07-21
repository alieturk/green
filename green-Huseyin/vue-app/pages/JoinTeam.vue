<template>
  <navbar-component></navbar-component>
  <div class="flex flex-col justify-center items-center mt-20 ">
    <div class="w-100 space-y-2">
      <!-- Header-->
      <div class="header">
        <h1 class=" text-2xl text-emerald-800 md:text-3xl xl:text-3xl font-bold tracking-tight">Thanks for joining our
          community</h1>
      </div>
      <div class="sub-title right-0">
        <p class="mb-4 text-lg font-normal text-gray-500 lg:text-xl dark:text-gray-400">create a new group or join an
          existing one</p>
      </div>
      <button class="bg-yellow-400 hover:bg-yellow-500 text-emerald-900 text-sm px-3 py-1 border rounded-full"
              @click="pushToCreateTeamPage">Create a
        new group
      </button>
    </div>
    <div>
      <!-- OTP input-->
      <div class="mt-20 ">
        <p class="text-sm text-center text-emerald-800">Enter your code</p>
        <div class=" flex flex-col items-center justify-center">
          <div class="flex flex flex-row rounded-md border-2 border-gray-300">
            <v-otp-input
                ref="otpInput"
                :conditionalClass="['one', 'two', 'three', 'four', 'five']"
                :is-input-num="true"
                :num-inputs="5"
                :placeholder="['_', '_', '_', '_', '_']"
                :should-auto-focus="true"
                input-classes="otp-input h-11 w-11 text-center text-sm p-2"
                separator=""
                @on-complete="handleOnComplete"
            />
          </div>
          <!-- Error messages-->
          <p v-if="active === 'invalid'" class="md:font-bold text-red-700 hover:text-red-800">There is no group with
            this code</p>
          <p v-if="active === 'assigned'" class="md:font-medium text-blue-700 hover:text-blue-800"
             @click="pushToContactPage">You have already assigned to an another team. If it is not correct, you are
            welcome to contact with us</p>
        </div>
      </div>
    </div>
  </div>
  <router-view></router-view>
  <footer-component></footer-component>
</template>

<script>
import NavbarComponent from "@/components/NavbarComponent";
import FooterComponent from "@/components/FooterComponent";
import router from "@/routes/router";
import VOtpInput from 'vue3-otp-input';
import {ref} from 'vue';


export default {
  inject: ["groupService"],
  name: "JoinTeam",
  components: {
    NavbarComponent,
    FooterComponent,
    VOtpInput,
  },
  //Composition API to handle the OTP input
  setup() {
    const otpInput = ref(null);

    const clearInput = () => {
      otpInput.value.clearInput();
    };
    return {clearInput, otpInput}
  },
  /**
   * @returns {{active: string}}
   */
  data() {
    return {
      active: '',
    }
  },

  methods: {
    pushToContactPage() {
      router.push('/contact')
    },
    pushToCreateTeamPage() {
      router.push('/createTeam')
    },
    /**
     *Calling fetch method
     * Handling responses and In result of response showing appropriate result
     * @param value
     */
    handleOnComplete(value) {
      this.groupService.asyncPostCode({code: value}, this.$store.getters.user.emailAddress)
          .then((response) => {
            if (response.status === 201) {
              setTimeout(() => {
                this.$router.push("/dashboard")
              }, 3000)
            } else if (response.status === 404) {
              this.clearInput();
              this.active = 'invalid'
            } else if (response.status === 403) {
              this.clearInput();
              this.active = 'assigned'
            }
          })
    }
  },

}
</script>

<style scoped>

input::placeholder {
  font-size: 13px;
  text-align: center;
  font-weight: 600;
}
</style>