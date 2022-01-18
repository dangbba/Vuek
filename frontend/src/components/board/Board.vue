<template>
  <p>게시판페이지</p>
  <swiper
    class="swiper"
    :options="swiperOption"
  >
    <swiper-slide>Try</swiper-slide>     <swiper-slide>resize</swiper-slide>     <swiper-slide>the</swiper-slide>     <swiper-slide>browser</swiper-slide>     <swiper-slide>window</swiper-slide>     <swiper-slide>Slide 6</swiper-slide>     <swiper-slide>Slide 7</swiper-slide>     <swiper-slide>Slide 8</swiper-slide>     <swiper-slide>Slide 9</swiper-slide>     <swiper-slide>Slide 10</swiper-slide>     <div
      class="swiper-pagination"
      slot="pagination"
    ></div>   </swiper> </template> 
<script lang="ts">
import Vue from "vue";
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css";
//import "swiper/swiper-bundle.css";

//import store from "@/src/store";
export default Vue.extend({
  components: {
    Swiper,
    SwiperSlide
  },
  data() {
    return {
      swiperOption: {
        slidesPerView: 5,
        spaceBetween: 50,
        pagination: {
          el: ".swiper-pagination",
          clickable: true
        },
        breakpoints: {
          1024: {
            slidesPerView: 4,
            spaceBetween: 40
          },
          768: {
            slidesPerView: 3,
            spaceBetween: 30
          },
          640: {
            slidesPerView: 2,
            spaceBetween: 20
          },
          320: {
            slidesPerView: 1,
            spaceBetween: 10
          }
        }
      }
    };
  },
  created() {},
  methods: {},
  computed: {}
});
</script>