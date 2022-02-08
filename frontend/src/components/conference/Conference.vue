<template>
  <div class="container">
    <div class="header">
      <h1 class="fw-bold my-5">Conference List</h1>
    </div>

    <conference-search></conference-search>

    <conference-create></conference-create>
    <div class="row">
      <conference-sort class="offset-5 col-4"></conference-sort>
      <conference-filter class="col-3"></conference-filter>
    </div>

    <conference-list></conference-list>
  </div>
</template>

<script>
import ConferenceList from "./ConferenceList.vue";
import ConferenceFilter from "./ConferenceFilter.vue";
import ConferenceSearch from "./ConferenceSearch.vue";
import ConferenceSort from "./ConferenceSort.vue";
import ConferenceCreate from "./ConferenceCreate.vue";
export default {
  name: "Conference",
  components: {
    ConferenceList,
    ConferenceFilter,
    ConferenceSearch,
    ConferenceSort,
    ConferenceCreate,
  },
  data() {
    return {
      text: "",
      roomCredentials: {
        name: "",
        content: "",
        file: "",
      },
      //
      someOtherProperty: null,
      dd: {
        originalValue: [],
        ddSelectedOption: "업무",
        disabled: false,
        readonly: false,
        visible: true,
        color: "",
        options: [
          {
            value: "업무",
            text: "업무",
          },
          {
            value: "교육",
            text: "교육",
          },
          {
            value: "기타",
            text: "기타",
          },
        ],
      },
    };
  },
  computed: {
  },
  methods: {
    //
    changeItem: async function () {
      //grab some remote data
      try {
        let response = await this.$http.get(
          "https://www.example.com/api/" + this.dd.ddSelectedOption + ".json"
        );
        console.log(response.data);
        this.someOtherProperty = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    resetValue() {
      this.$router.push("/");
    },
    //
  },
  watch: {},
  async created() {},
  resetValue() {
    this.$router.push("/");
  },
};
</script>

<style>
body {
  margin: 0;
  padding: 0;
}

#app {
  font-family: "Open Sans", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #000;
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;

  .header {
    background: #fff;
    color: #333132;
    font-weight: 400;
    padding: 16px 20px;
    box-shadow: 0 4px 24px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    font-size: 21px;

    svg {
      height: 30px;
      margin-right: 8px;
    }
  }

  .page-content {
    flex-grow: 1;
    display: flex;
    overflow: hidden;

    @media all and (max-width: 768px) {
      flex-direction: column;
    }
  }
}

.main-panel {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  background: #333;
  padding: 0 20px 20px 20px;
}

.video-navigation {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  padding: 6px 0;
  color: #ccc;

  > a,
  > span {
    padding: 6px 10px;
    line-height: 1em;
    font-weight: bold;
  }

  a {
    color: #fff;
    text-decoration: none;
    border-radius: 4px;

    &:hover {
      text-decoration: underline;
      cursor: pointer;
    }

    &.active {
      color: #333;
      background: #fff;

      &:hover {
        cursor: default;
        text-decoration: none;
      }
    }
  }
}

@media all and (max-width: 768px) {
  .video-navigation {
    display: none;
  }

  .main-panel {
    padding: 20px;
  }
}

.but {
  color: white;
  margin: 10px;
}
.icon-bar {
  height: 100%;
  width: 90px;
  background-color: #555;
  position: sticky; /* Fixed Sidebar (stay in place on scroll) */
  /*z-index: 1;*/ /* Stay on top */
  bottom: 0; /* Stay at the top */
  left: 0;
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 0;
}

.icon-bar a {
  display: block;
  text-align: center;
  padding: 16px;
  transition: all 0.3s ease;
  color: white;
  font-size: 36px;
}

.icon-bar a:hover {
  background-color: #858585;
}

.active {
  background-color: #5eaf13;
}

.loginmodal {
  margin: 2rem;
  font-size: 1.8rem;
}

.signupmodal {
  margin: 2rem;
  font-size: 1.8rem;
}
</style>
