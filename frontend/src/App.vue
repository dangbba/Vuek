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
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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
</style>
