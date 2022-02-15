<template>
  <div id="app">
    <navi-bar />
    <router-view />
    <spinner :loading="$store.state.LoadingStatus" />
  </div>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";
import Spinner from "./components/layout/Spinner.vue";
export default {
  name: "App",
  components: {
    NaviBar,
    Spinner,
  },
};
</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Dongle:wght@700&family=Jua&display=swap');


* {
  font-family: 'Gothic A1', sans-serif;
}

#app {
  background-color: #343a40;
  color: whitesmoke;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

h2 {
  border-left:6px solid #0b5ed7;
  padding-left: 12px;
  text-shadow: 2px 2px 2px gray;
  color: #ffffff;  
/* background-color: #E1F5FE;
  text-shadow: 4px 4px 0px #bdbdbd; */
}

.close {
  font-size: 1.5em;
  color: #858585;
  border: 0;
  outline: 0;
  background-color: transparent
} 

</style>
