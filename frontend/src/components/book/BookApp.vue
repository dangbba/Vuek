<template>
  <div id="app">
    <Book
      title="The Thinker's Guide to The Art of Socratic Questioning"
      cover="https://images-na.ssl-images-amazon.com/images/I/81WmLFAO4yL.jpg"
      authors="Richard Paul, Linda Elder"
      link="https://maciejsmolinski.com/books/socratic"
    />
    <Book
      title="Docker in Action"
      cover="https://images-na.ssl-images-amazon.com/images/I/81J4b6i9KyL.jpg"
      authors="Jeff Nickoloff"
    />
    <Book
      title="On Writing Well: The Classic Guide to Writing Nonfiction"
      cover="https://images-na.ssl-images-amazon.com/images/I/610AIYRtaDL.jpg"
      authors="William Zinsser"
      link="https://maciejsmolinski.com/books/writing"
    />
    <Book
      title="Extreme Programming Explained: Embrace Change"
      cover="https://images-na.ssl-images-amazon.com/images/I/51iupjtHU%2BL._SX342_QL70_ML2_.jpg"
      authors="Kent Beck"
    />
    <Book
      title="Site Reliability Engineering: How Google Runs Production Systems"
      cover="https://images-eu.ssl-images-amazon.com/images/I/51XswOmuLqL.jpg"
      authors="Betsy Beyer, Chris Jones, Jennifer Petoff, Niall Richard Murphy"
    />
    <Book
      title="Structure and Interpretation of Computer Programs"
      cover="https://images-na.ssl-images-amazon.com/images/I/51H17R%2BbW8L._SX331_BO1,204,203,200_.jpg"
      authors="Harold Abelson, Geerald Jay Sussman, Julie Sussman"
    />
  </div>
</template>

<script>
import Book from "./components/BookHorizontal";

export default {
  name: "App",
  components: {
    Book
  }
};
</script>

<style>
html {
  background: #100B20;
}
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #D8D8D8;
  margin-top: 60px;
}
</style>
