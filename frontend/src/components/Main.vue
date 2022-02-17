<template>
  <!--vue 버전 호환성 때문인지 안되는 기능들이 몇가지 있음
      -> slide,fade 애니메이션 사용하면 부자연스러운 깜빡임 있음
      -> 하단 indicators 안됨 
      (최신 부트스트랩(cdn)을 쓰면 가능하기는 함) -->
  <div>
    <b-carousel
      :interval="10000"
      no-animation
      indicators
      background="#000000"
      class="carousel slide"
      controls
      label-next
      label-prev
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
    >

      <b-carousel-slide v-for="p in phaseList" :key="p.no">
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            :src="require(`@/assets/main/main_img_${p.no}.jpg`)"
            alt="main-image"
          >
        </template>
        <div class="d-none d-sm-block">
          <h1 :class="{ 'pjt-name' : p.no === 1}">{{ p.phase }}</h1>
          <h5><span class="ko">{{ p.phase_ko }}</span>  {{ p.writer }}</h5>
        </div>
      </b-carousel-slide>

    </b-carousel>

  </div>
</template>

<script>

  export default {
    name: "Main",
    data() {
      return {
        phaseList: [
          { no : 1,
            phase : '모두의 책갈피',
            phase_ko : '',
            writer : '' },
          { no : 2,
            phase : 'We read to know we are not alone.',
            phase_ko : '우리는 혼자가 아니라는 것을 알기 위해 독서를 한다.',
            writer : '- C.S. Lewis' },
          { no : 3,
            phase : 'When you learn to read you will be born again... and you will never be quite so alone again.',
            phase_ko : '당신이 독서를 할 때 당신은 다시 태어날 것이다. 그리고 결코 다시는 외롭지 않을 것이다.',
            writer : '- Rumer Godden' },
          { no : 4,
            phase : 'Live always in the best company when you read.',
            phase_ko : '독서할 때 당신은 항상 가장 좋은 친구와 함께 있다.',
            writer : '- Sydney Smith' },
          { no : 5,
            phase : 'Life-transforming ideas have always come to me through books.',
            phase_ko : '나는 삶을 변화시키는 아이디어를 항상 책에서 얻었다.',
            writer : '- Bell Hooks' },
          // { no : 6,
          //   phase : 'Books are the plane, and the train, and the road. They are the destination, and the journey. They are home.',
          //   phase_ko : '책들은 비행기이자 기차이며 길이다. 그것은 목적지이자 여행이고 집이다.',
          //   writer : 'Anna Quindlen' }
        ]
      }
    },
    methods: {
    }
  }
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Raleway:ital,wght@1,900&display=swap&family=Gothic+A1:wght@600&display=swap');

* {
  font-family: 'Raleway', sans-serif;
}

.ko {
  font-family: 'Gothic A1', sans-serif;
}
.pjt-name {
  font-size: 50px;
  /* text-shadow: 2px 5px 4px gray; */
  text-shadow: grey 1px 0 10px;
  font-family: "Arial Black", sans-serif;
  font-weight: bold;
  color: #ffffff;
  text-align: start;
  margin-bottom: 25rem;
  margin-left: 3rem;
  /* background: linear-gradient(to top, grey 50%, transparent 50%) */
  /* padding: 15px;
  background-color: rgba(189, 187, 187, 0.4);
  width: 100%; */
  /* text-decoration: underline; */
}

img {
  vertical-align: bottom;
}
</style>
