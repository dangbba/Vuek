<template>
  <div>
    베스트셀러/신간

    <div>
      베스트셀러
      <carousel-3d
        :disable3d="true"
        :space="365"
        :clickable="false"
        :controls-visible="true"
      >
        <slide
          v-for="(slide, i) in slides"
          :index="i"
          :key="i"
          :title="slide.title"
        >
          <a class="book" :href="link" target="_blank">
            <div class="book__cover">
              <img :src="cover" />
            </div>
            <div class="book__details">
              <h1 class="book__title">{{ title }}</h1>
              <h2 class="book__authors">{{ authors }}</h2>
            </div>
          </a>
        </slide>
      </carousel-3d>
    </div>

    <div>
      신간
      <carousel-3d
        :disable3d="true"
        :space="365"
        :clickable="false"
        :controls-visible="true"
      >
        <slide v-for="(slide, i) in slides" :index="i" :key="i">
          <a class="book" :href="link" target="_blank">
            <div class="book__cover">
              <img :src="cover" />
            </div>
            <div class="book__details">
              <h1 class="book__title">{{ title }}</h1>
              <h2 class="book__authors">{{ authors }}</h2>
            </div>
          </a>
        </slide>
      </carousel-3d>
    </div>
  </div>
</template>

<script>
import * as Carousel3d from "vue-carousel-3d";
import http from "@/config/http-common.js";

export default {
  name: "Book",
  props: {
    author: String,
    publisher: String,
    title_url: String,
    overview: String,
    title: String,
    publish_date: String,
    authors: String,
    cover: String,
    link: String,
  },
  components: {
    "carousel-3d": Carousel3d.Carousel3d,
    slide: Carousel3d.Slide,
  },
  computed: {
    slidesPerPage() {
      return this.items.length >= 6 ? 6 : this.items.length;
    },
    wrapperWidth() {
      return 160 * this.slidesPerPage;
    },
  },
  data() {
    const items = [];

    return {
      showBooks: window.innerWidth <= 768 ? true : false,
      items,
      slides: 5
    };
  },
  methods: {
    switchContainer: function () {
      return (this.showBooks = !this.showBooks);
    },
  },

  registMember() {
    http.get(`/book`, {
      id: this.id,
      author: this.author,
      publisher: this.publisher,
      title_url: this.title_url,
      overview: this.overview,
      price: this.price,
      title: this.title,
      publish_date: this.publish_date,
    });
  },
};
</script>

<style scoped>
.bg {
  margin: 0 auto;
  background-color: grey;
}
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

body {
  margin: 0;
  background: #f7f7f7;
}

.navbar {
  padding: 15px;
  background: #181c2b;
  display: flex;
  z-index: 4;
  align-items: center;
  justify-content: flex-start;
}

.navbar h2 {
  margin: 0;
  color: #eee;
  width: 25%;
}

.navbar h2 .logo {
  padding: 8px;
  border-radius: 4px;
  margin-right: 8px;
  background: #3940ff;
  box-shadow: -4px 4px #373cc3;
}

.data-search {
  width: 60%;
}

.data-search input {
  border: 0;
  border-radius: 2px;
}

.data-search ul {
  border: 1px solid #eee;
  border-radius: 2px;
  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
}

.filters-container {
  display: flex;
  flex-direction: column;
  width: 25%;
  padding: 10px;
}

.container {
  display: flex;
  flex-direction: row;
}

.filter {
  background: white;
  padding: 10px;
  margin: 10px 0;
  box-shadow: 0 0 2px rgba(24, 28, 43, 0.2);
  color: #181c2b;
}

.result-list-container {
  overflow: hidden;
  width: 75%;
  margin-bottom: 10px;
  padding: 10px;
}

.books-container {
  display: flex;
  padding: 10px;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
}

.book-content {
  flex-grow: 1;
  width: 200px;
  max-width: 250px;
  margin: 10px;
  transition: all ease 0.1s;
  overflow: hidden;
}

.image {
  text-align: center;
  position: relative;
}

.image img {
  height: 290px;
}

.rating {
  position: absolute;
  left: 250px;
  top: 0px;
  transition: all ease 0.2s;
  padding: 5px 8px;
  display: flex;
  align-items: center;
  font-weight: 700;
  border-radius: 2px;
  background: rgba(24, 28, 43, 0.8);
  color: #eee;
}

.details {
  position: absolute;
  left: -300px;
  width: 100%;
  bottom: 0;
  border-radius: 2px;
  padding: 5px 8px;
  transition: all ease 0.2s;
  background: rgba(24, 28, 43, 0.9);
}

.book-content:hover .details {
  left: 0;
  text-align: left;
}

.details h4,
p {
  margin: 5px 0;
}

.details h4 {
  color: #eee;
}

.details p {
  color: #f7f7f7;
}

.book-content:hover .rating {
  left: 0;
}

.toggle {
  display: none;
}

.appbase {
  display: none;
  padding: 0;
  width: 0;
}

@media (max-width: 768px) {
  .toggle {
    padding: 5px 8px;
    color: #eee;
    background: #0b6aff;
    position: fixed;
    bottom: 0;
    font-size: 12px;
    width: 100%;
    z-index: 2;
    display: inline;
    outline: none;
    cursor: pointer;
  }

  .navbar {
    justify-content: space-between;
  }

  .data-search {
    width: 40%;
  }

  .filters-container {
    display: none;
  }

  .result-list-container.full {
    width: 100%;
    display: block;
  }

  .result-list-container {
    width: 0%;
    display: none;
  }

  .filters-container.full {
    width: 100%;
    display: block;
  }
}

.book {
  width: 350px;
  display: flex;
  color: inherit;
  text-decoration: none;

  flex-direction: column;
  margin: 20px 5px 60px;
  text-align: center;
}
.book_details {
}
.book__cover {
}
.book__cover > img {
  max-width: 90px;
  border-radius: 4px;
  filter: grayscale(0.8) saturate(1.6) opacity(0.8);
}
.book__title {
  font-family: "Gentium Book Basic", serif;
  font-size: 1.25em;
  margin: 1em 0;
}
.book__authors {
  font-family: "Montserrat", sans-serif;
  font-size: 0.9em;
  margin: 0 0 1em;
  opacity: 70%;
}
</style>
