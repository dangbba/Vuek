<template>
  <div>
      베스트셀러/신간

    <carousel :autoplay="true" :nav="false" :dots="false" class="marginTop50">
    <div> 베스트셀러 </div>
    </carousel>
    <carousel :autoplay="true" :nav="false" :dots="false" class="marginTop50">
      <img src="https://placeimg.com/200/200/any?1">
      <img src="https://placeimg.com/200/200/any?2">
      <img src="https://placeimg.com/200/200/any?3">
      <img src="https://placeimg.com/200/200/any?4">
    </carousel>
    
    <carousel :autoplay="true" :nav="false" :dots="false" class="marginTop50">
    <div> 신간 </div>
    </carousel>
    <carousel :autoplay="true" :nav="false" :dots="false" class="marginTop50">
      <img src="https://placeimg.com/200/200/any?1">
      <img src="https://placeimg.com/200/200/any?2">
      <img src="https://placeimg.com/200/200/any?3">
      <img src="https://placeimg.com/200/200/any?4">
    </carousel>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>