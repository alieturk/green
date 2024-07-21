<template>
    <router-view></router-view>
</template>

<script>

import {GroupService} from "@/services/GroupService";
import {RegisterService} from "@/services/registerService"
import {ResearchItemService} from "@/services/ResearchItemService";
import {UserService} from "@/services/UserService";
import {CommunityService} from "@/services/CommunityService";

export default {
  name: 'App',

  provide() {
    return {
      //service for manipulating community posts
      communityService: new CommunityService(),
      
      //service for manipulating Group entities
      groupService: new GroupService(),

      //service for registering a new user
      registerService: new RegisterService(),

      //service for manipulating research item entities
      researchItemService: new ResearchItemService(),

      //service for manipulating user entities
      userService: new UserService()
    }
  }
}

</script>

<style>
 body{
   @apply m-0 bg-white
 }
</style>
