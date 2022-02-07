<template>
  <div>
    <book-search></book-search>

    <div class="wrap">
      <div class="carousel-wrap">
        <h2>
          베스트셀러
          <carousel v-bind="options" @initialized="init" @changed="changed">
            <a
              href="http:\/\/www.aladin.co.kr\/shop\/wproduct.aspx?ItemId=286778297&amp;partner=openAPI&amp;start=api"
            >
              <img
                :src="`https:\/\/image.aladin.co.kr\/product\/28677\/82\/coversum\/8934952318_1.jpg`"
              />
            </a>
            <a
              href="http:\/\/www.aladin.co.kr\/shop\/wproduct.aspx?ItemId=286778398&amp;partner=openAPI&amp;start=api"
            >
              <img
                :src="`https:\/\/image.aladin.co.kr\/product\/28677\/83\/coversum\/8934952326_1.jpg`"
              />
            </a>
            <img
              :src="`https:\/\/image.aladin.co.kr\/product\/26942\/84\/coversum\/k582730818_1.jpg`"
            />
            <img
              :src="`https:\/\/image.aladin.co.kr\/product\/28465\/73\/coversum\/k092835920_1.jpg`"
            />
            <img
              :src="`https:\/\/image.aladin.co.kr\/product\/28730\/75\/coversum\/k712836673_1.jpg`"
            />
            <img
              :src="`https:\/\/image.aladin.co.kr\/product\/28689\/95\/coversum\/k332836361_1.jpg`"
            />
          </carousel>
        </h2>
      </div>
      <hr />
      <div class="carousel-wrap">
        <h2>
          새로나온책
          <carousel v-bind="options" @initialized="init" @changed="changed">
            <a href="https://maciejsmolinski.com/books/socratic">
              <img
                src="https://images-na.ssl-images-amazon.com/images/I/81WmLFAO4yL.jpg"
              />
            </a>
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/81J4b6i9KyL.jpg"
            />
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/610AIYRtaDL.jpg"
            />
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/51iupjtHU%2BL._SX342_QL70_ML2_.jpg"
            />
            <img
              src="https://images-eu.ssl-images-amazon.com/images/I/51XswOmuLqL.jpg"
            />
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/51H17R%2BbW8L._SX331_BO1,204,203,200_.jpg"
            />
          </carousel>
        </h2>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/config/http-common.js";
import carousel from "vue-owl-carousel2";
import BookSearch from "./BookSearch.vue";

export default {
  name: "book",
  components: {
    carousel,
    BookSearch,
  },
  data() {
    return {
      plugin: null,
      result: [],
      options: {
        autoplay: false,
        items: 5,
        startPosition: 0,
        autoplayTimeout: 1000,
      },
    };
  },
  // data지정까지만 해도 동작함
  methods: {
    handleScroll() {
      window.addEventListener("scroll", () => {
        let scrollT = window.scrollY;

        if (scrollT > 0 && this.options.autoplay === false) {
          this.options.autoplay = true;
          setTimeout(() => {
            this.plugin.refresh();
          }, 300);
        } else if (scrollT === 0) {
          this.options.autoplay = false;
          setTimeout(() => {
            this.plugin.refresh();
          }, 300);
        }
      });
    },
    init() {
      this.plugin = this.$children[0];
    },
    changed(e) {
      this.options.startPosition = e.item.index;
    },
  },
  mounted() {
    this.handleScroll();
  },
  destroyed() {
    window.removeEventListener("scroll", this.handleScroll);
  },

  created() {
    http({
      method: "get",
      url: `/search/bestseller`,
    })
      .then((response) => {
        console.log(response.data.item);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style scoped>
.wrap {
  border: 1px solid#000;
  height: 3000px;
}
.carousel-wrap {
  width: 600px;
  margin: 0 auto;
}
</style>
