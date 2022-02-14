// import http from "@/config/http-common.js";
// import router from "@/router/index.js";
// import store from "..";

// const chatStore = {
//   namespaced: true,
//   state: {
//     username: null,
//     stompClient: null,
//   },
//   mutations: {
//     LOAD_CONNECT: function (event) {
//       if(username) {
//         usernamePage.classList.add('hidden');
//         chatPage.classList.remove('hidden');

//         var socket = new SockJS('/api');
//         stompClient = Stomp.over(socket);

//         stompClient.connect({}, onConnected, onError);
//       }
//       event.preventDefault();
//     },
//     LOAD_ON_CONNECTED: function () {
//       stompClient.subscribe('/topic/public', onMessageReceived);
//       stompClient.send(
//         "/app/chat.register",
//         {},
//         JSON.stringify({sender: username, type: 'JOIN'})
//       )
//     },
//     LOAD_ONERROR: function (error) {
//       connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//       connectingElemnet.style.color = 'red';
//     },
//     LOAD_SEND: function (event) {
//       var messageContent = messageInput.value.trim();

//       if(messageContent && stompClient) {
//         var chatMessage = {
//           sender: username,
//           content: messageInput.value,
//           type: 'CHAT'
//         };

//         stompClient.send("/", {}, JSON.stringify(chatMessage));
//         messageInput.value = '';
//       }
//       event.preventDefault();
//     }
//   }
// }

// export default chatStore;