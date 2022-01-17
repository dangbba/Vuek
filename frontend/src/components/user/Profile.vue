<template>
<div>
  <vue-profile 
  nickname="@Lukenoutte"
  cardWidth="600"
  bodyPhrase="Follow the white rabbit."
  :socialLinks="myLinks"
  :profileImg="myLinks.img"
  :facebook="facebook"
  />
</div>

</template>

<script>
import img from "@/assets/logo.png"

export default {
  data(){
    return{
      myLinks: { 
        git: "https://lab.ssafy.com/s06-webmobile1-sub1/S06P11C205",         
        link: "https://naver.com", 
        img,
        facebook: "https://facebook.com"
        },
    }
  }
}
</script>

<style scoped>

</style>