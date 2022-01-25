export default {
  startSpinner(state) {
    state.LoadingStatus = true;
  },
  endSpinner(state) {
    state.LoadingStatus = false;
  },
};
