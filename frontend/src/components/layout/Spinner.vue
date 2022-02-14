<template>
  <div class="text-center">
    <b-spinner
      style="width: 3rem; height: 3rem"
      variant="primary"
      class="m-5"
      v-if="loading"
    >
    </b-spinner>
    <div></div>
    <div></div>
    <div></div>
    <div></div>
  </div>
</template>
<script>
export default {
  name: "spinner",
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
  },
};
</script>
<style>
.load-spinner {
  display: inline-block;
  position: absolute;
  width: 64px;
  height: 64px;
  top: 47%;
  left: 47%;
}
.load-spinner div {
  display: inline-block;
  position: absolute;
  left: 6px;
  width: 13px;
  background: #42b883;
}
.load-spinner div:nth-child(1) {
  left: 6px;
  animation-delay: -0.24s;
}
.load-spinner div:nth-child(2) {
  left: 26px;
  animation-delay: -0.12s;
}
.load-spinner div:nth-child(3) {
  left: 45px;
  animation-delay: 0;
}
@keyframes load-spinner {
  0% {
    top: 6px;
    height: 51px;
  }
  50%,
  100% {
    top: 19px;
    height: 25px;
  }
}
</style>
