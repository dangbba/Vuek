<template>
  <div id="app">
    유저이름: 
    <input
      v-model="userName"
      type="text"
    >
    내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    >
    <div
      v-for="(item, idx) in recvList"
      :key="idx"
    >
      <h3>유저이름: {{ item.userName }}</h3>
      <div class="chat-container">
      <div class="chat-header">
        <h2>Chat Box</h2>
      </div>
      <div class="connecting">Connecting...</div>
      <ul id="messageArea">
      </ul>
      <form id="messageForm" name="messageForm" nameForm="messageForm">
        <div class="form-group">
          <div class="input-group clearfix">
            <input type="text" id="message" placeholder="메시지를 입력하세요." autocomplete="off" class="form-control" />
          <button type="submit" class="primary">Send</button>
          </div>
        </div>
      </form>
    </div>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'ChatChat',
  data() {
    return {
      userName: "",
      message: "",
      recvList: []
    }
  },
  created() {
    // ChatChat.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },    
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          userName: this.userName,
          content: this.message 
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },    
    connect() {
      const serverURL = "https://i6c205.p.ssafy.io:8080/api/v1"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );        
    }
  }
}
</script>