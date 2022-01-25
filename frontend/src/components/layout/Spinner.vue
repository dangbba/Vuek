<template>
  <div class="text-center">
    <b-spinner
      style="width: 3rem; height: 3rem"
      variant="primary"
      class="m-5"
      v-if="loading"
    >
    </b-spinner>
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
.lds-facebook {
  display: inline-block;
  position: absolute;
  width: 64px;
  height: 64px;
  top: 47%;
  left: 47%;
}
.lds-facebook div {
  display: inline-block;
  position: absolute;
  left: 6px;
  width: 13px;
  background: #42b883;
  animation: lds-facebook 1.2s cubic-bezier(0, 0.5, 0.5, 1) infinite;
}
.lds-facebook div:nth-child(1) {
  left: 6px;
  animation-delay: -0.24s;
}
.lds-facebook div:nth-child(2) {
  left: 26px;
  animation-delay: -0.12s;
}
.lds-facebook div:nth-child(3) {
  left: 45px;
  animation-delay: 0;
}
@keyframes lds-facebook {
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
