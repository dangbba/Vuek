<template>
  <div class="about">
    <conference-history></conference-history>
  </div>
</template>
<script>

import ConferenceHistory from "@/components/conference/ConferenceHistory.vue";

export default {
  name: "About",
  components: {
    ConferenceHistory
  },
  
}
</script>
