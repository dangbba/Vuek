<template>
  <div class="hello">
    회의목록 보여주기
    <div id="app">
      <carousel-3d
        :controls-visible="true"
        :clickable="false"
        :autoplay="true"
        :autoplayTimeout="3500"
        :autoplayHoverPause="true"
        :perspective="0"
      >
        <slide v-for="(slide, i) in slides" :key="slide" :index="i">
          <figure>
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/81WmLFAO4yL.jpg"
            />
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/81J4b6i9KyL.jpg"
            />
            <figcaption>각 페이지</figcaption>
          </figure>
        </slide>
      </carousel-3d>

      리뷰
      <carousel-3d :space="300" :width="200" :height="100">
        <slide v-for="(slide, i) in slides" :key="i" :index="i" class="item">
          <span class="title">리뷰</span>
          <p>내용</p>
        </slide>
      </carousel-3d>
    </div>
  </div>
</template>

<script>
import { Carousel3d, Slide } from "vue-carousel-3d";

export default {
  name: "Main",
  components: {
    Carousel3d,
    Slide,
    "carousel-3d": Carousel3d,
    slide: Slide,
  },
  data() {
    return {
      slides: 5,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.carousel-3d-container .current figure {
  margin: 0;
}

.carousel-3d-container .carousel-3d-slide.current figcaption {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  bottom: 0;
  position: absolute;
  bottom: 0;
  padding: 15px;
  font-size: 12px;
  min-width: 100%;
  box-sizing: border-box;
}

.center {
  transform: translateY(-90%);
}
.item {
  height: auto !important;
}
</style>
