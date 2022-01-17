# 하루 일과 정리 및 TIL

9:00 ~ 10:00 : 유튜브 강의 시청 및 전체 팀 미팅

10 : 00 ~ 10:30 : 팀 스크럼 및 아이디어 추가 회의

10 : 30 ~ 12:30 : IntelliJ 오류 수정 및 구동 확인, 팀원 오류 디버그 및 회의, signup/login form 수정



1:00 ~ 1:20 : 팀 추가 아이디어 회의 - 독서모임 관련 화상회의 웹

1:30 ~ 3:00 : signup action 공부 및 작성, profile router 생성

3:00 ~ 4:25 : Web RTC 기초공부, 컨설턴트님과의 회의를 위한 회의록 준비



도서검색 API 

https://steemit.com/kr/@anpigon/open-api 도서검색api 비교자료





## 독서모임을 위한 화상회의 웹

​	idea 시작 : 독서를 메인으로 하여 관련된 기능을 모아놓은 화상회의 플랫폼은 찾기 힘들다 ( 기존의 zoom, naver band를 많이 이용)

	- 실시간으로 도서 정보를 검색 or 공유할 수 있는 웹 사이트
	- 개인의 독서 의욕을 높이기 위한 시스템을 만들기

1. 타겟 :  독서회 & 관련모임을 원하는 사람들

   1. 목적 : 각자 읽은 도서정보 공유 및 실시간 도서 추천 기능, 게시판 기능을 활용한 도서리뷰&독후감 

2. 핵심기능

   1. 도서정보 API(국립중앙도서관, 인터파크 API)를 활용하여 도서정보를 활용
   2. 화상회의 발언자가 읽은/관심도서를 검색 후 화면or사이드에 띄울 수 있음

3. 추가기능

   1. 프로필 + 

      1. 프로필에 각자 읽은 도서목록 업데이트 + 읽은 도서 수에 따라서 레벨or업적or훈장 시스템 도입 - 독서장려
      2. 본인의 관심사를 카테고리에 따라서 설정하여 후에 베스트셀러 추천 기능에 활용하거나 게시판에서 카테고리 따라서 정렬 기능 추가(고려)
      3. 독서마라톤 챌린지
         1. 시작시간, 종료시간, 목표량(쪽수) 설정 후 독서마라톤 시작
         2. 프로필에 읽은 책 정보 등록 후 프로필의 독서마라톤에 읽은 양 등록 - 독서의욕 고취 유도

   2. 게시판 기능

      1. 도서리뷰 및 추천 게시판을 만들어서 사용자들이 서로 도서를 추천/추천받을 수 있도록 유도

   3. 심화기능

      1. 인터파크 도서검색 API - 베스트셀러 추천 기능

         1. 베스트셀러 추천 시 프로필 상의 카테고리를 기반으로 검색하여 화면에 띄워줌+인터파크 홈페이지로 도서구매 이동(?)

      2. 신간도서 추천기능

         1. API를 활용하여 최근에 출판된 도서들 중 신간도서들을 종합하여 기준(랜덤, 가격, 프로필 상에 등록된 관심 카테고리 등)에 따라서 랜덤으로 뜨게 만들 수 있다.

         

         

      

# Web RTC 기본 공부내용



![img](https://media.vlpt.us/images/ehdrms2034/post/7c3a70bd-2950-4d1d-885a-2f761b6fd256/image.png)

Web RTC 기술은 P2P 통신에 최적화 되어있다.
Web RTC에 사용되는 기술은 여러가지가 있지만 크게 3가지의 클래스에 의해서 실시간 데이터 교환이 일어난다.

- MediaStream - 카메라/마이크 등 데이터 스트림 접근
- RTCPeerConnection - 암호화 및 대역폭 관리 및 오디오 또는 비디오 연결
- RTCDataChannel - 일반적인 데이터 P2P통신

이 3가지의 객체를 통해서 데이터 교환이 이뤄지며 RTCPeerConnection들이 적절하게 데이터를 교환할 수 있게 처리하는 과정을 **시그널링(Signaling)** 이라고 한다.

위그림은 시그널링 하는 과정을 나타낸 것인데,**PeerConnection**은 두 명의 유저가 스트림을 주고 받는 것이므로 연결을 요청한 콜러(caller)와 연결을 받는 콜리(callee)가 존재한다. 콜러와 콜리가 통신을 하기 위해서는 **중간 역할을 하는 서버**가 필요하고 서버를 통해서 **SessionDescription**을 서로 주고 받아야 한다.

## 간단한 용어 정리

Web RTC API를 들여다보면 참 익숙치 않은 메소드와 용어들이 자주 등장한다.
튜토리얼글을 싸지르기(?) 앞서, 혼자 분석하면서 어렵거나 이해하기 힘들었던 용어들을 간략하게 정리해보았다.

#### Stun Server , Turn Server

Web RTC는 P2P에 최적화 되어있다. 즉 Peer들간의 공인 네트워크 주소(ip)를 알아 데이터 교환을 해야하는데, 실제 개개인의 컴퓨터는 방화벽등 여러가지 보호장치들이 존재하고 있다.
그래서 Peer들간의 연결이 쉽지 않은데, 이렇게 서로간의 연결을 위한 정보를 공유하여 P2P 통신을 가능하게 해주는 것이 Stun/Turn Server이다.

#### SDP (Session Description Protocol)

세션 기술 프로토콜(Session Description Protocol, SDP)은 스트리밍 미디어의 초기화 인수를 기술하기 위한 포맷이다. 이 규격은 IETF의 RFC 4566로 규정되어 있다.
실제로 WEB RTC는 SDP format 에 맞춰져 영상,음성 데이터를 교환하고 있다.

#### Ice (Interactive Connectivity Establishment)

NAT환경에서 자신의 Public IP를 파악하고 상대방에게 데이터를 전송하기 위한 Peer간의 응답 프로토콜로 일반적으로 STUN/TURN을 이용해서 구축을 한다.
간단하게 설명하면, 한쪽이 Offer를 보내면 다른 한쪽이 Answer함으로써 피어간 연결이 설정된다

## Web RTC를 이용하여 화상통화 구현하기

출처 : https://velog.io/@ehdrms2034/WebRTC-%EC%9B%B9%EB%B8%8C%EB%9D%BC%EC%9A%B0%EC%A0%80%EB%A1%9C-%ED%99%94%EC%83%81-%EC%B1%84%ED%8C%85%EC%9D%84-%EB%A7%8C%EB%93%A4-%EC%88%98-%EC%9E%88%EB%8B%A4%EA%B3%A0

주요 코드

- rtc.html

```html
<html>
<head>
    <meta charset="utf-8" />
    <title>WebRtc tutorial</title>
</head>

<body>
    <div>
        <video id="localVideo" autoplay width="480px"></video>
        <video id="remoteVideo" width="480px" autoplay></video>
    </div>

    <script src="/socket.io/socket.io.js"></script>
    <script src="./rtc.js"></script>
</body>

</html>
```

화상통화를 구현하기 위해서는 내 화면과 상대방의 화면을 나타낼 뷰를 제작하여 준다.
또한 시그널링과정에 필요한 socket.io cdn또한 설정하여 준다.

- rtc.js **: 내 영상 정보 가져오기**

```javascript
let localVideo = document.getElementById("localVideo");
let remoteVideo = document.getElementById("remoteVideo");
let localStream;

navigator.mediaDevices
  .getUserMedia({
    video: true,
    audio: false,
  })
  .then(gotStream)
  .catch((error) => console.error(error));

function gotStream(stream) {
  console.log("Adding local stream");
  localStream = stream;
  localVideo.srcObject = stream;
  sendMessage("got user media");
  if (isInitiator) {
    maybeStart();
  }
}
```

mediaDevice 객체의 getUserMedia Method를 통해서 사용자의 미디어 데이터를 스트림으로 받아올 수 있다. localStream과 localVideo에 출력할 영상을 본인 캠으로 지정한다.

- **sendMessage()**

```javascript
function sendMessage(message){
  console.log('Client sending message: ',message);
  socket.emit('message',message);
}
```

시그널링 서버로 소켓정보를 전송하는 메소드이다. 후에 많이 언급됌. 시그널링 서버, 다른 Peer로의 데이터를 전송하는 method라고 보면 된다.

- **rtc.js : RTC Peer 연결하기**

```javascript
function createPeerConnection() {
  try {
    pc = new RTCPeerConnection(null);
    pc.onicecandidate = handleIceCandidate;
    pc.onaddstream = handleRemoteStreamAdded;
    console.log("Created RTCPeerConnection");
  } catch (e) {
    alert("connot create RTCPeerConnection object");
    return;
  }
}

function handleIceCandidate(event) {
  console.log("iceCandidateEvent", event);
  if (event.candidate) {
    sendMessage({
      type: "candidate",
      label: event.candidate.sdpMLineIndex,
      id: event.candidate.sdpMid,
      candidate: event.candidate.candidate,
    });
  } else {
    console.log("end of candidates");
  }
}

function handleCreateOfferError(event) {
  console.log("createOffer() error: ", event);
}

function handleRemoteStreamAdded(event) {
  console.log("remote stream added");
  remoteStream = event.stream;
  remoteVideo.srcObject = remoteStream;
}
```

createPeerConnection을 통해 RTCPeerConnection에 대한 객체를 형성해주고 있다.
iceCandidate는 데이터 교환을 할 대상의 EndPoint 정보라고 보면 된다.
따라서 iceCandidate할 대상이 생긴다면 handleIceCandidate Method를 실행하게 된다.
이 부분은 signaling 서버로 넘겨줘 상대방 Peer가 내 Stream을 연결할 수 있도록 한다.
연결된 Peer는 handleRemoteStreamAdded Method를 통해서 remoteVideo 뷰에 띄우도록 한다.



```javascript
function maybeStart() {
  console.log(">>MaybeStart() : ", isStarted, localStream, isChannelReady);
  if (!isStarted && typeof localStream !== "undefined" && isChannelReady) {
    console.log(">>>>> creating peer connection");
    createPeerConnection();
    pc.addStream(localStream);
    isStarted = true;
    console.log("isInitiator : ", isInitiator);
    if (isInitiator) {
      doCall();
    }
  }else{
    console.error('maybeStart not Started!');
  }
}
```

maybeStart method는 자신의 RTCPeerConnection을 초기화하고 상대방의 RTCPeerConnection과 연결하는 함수이다.

실제로 연결이 됐다면 doCall함수를 실행시켜 데이터를 주고 받는다.



```javascript
function doCall() {
  console.log("Sending offer to peer");
  pc.createOffer(setLocalAndSendMessage, handleCreateOfferError);
}

function doAnswer() {
  console.log("Sending answer to peer");
  pc.createAnswer().then(
    setLocalAndSendMessage,
    onCreateSessionDescriptionError
  );
}

function setLocalAndSendMessage(sessionDescription) {
  pc.setLocalDescription(sessionDescription);
  sendMessage(sessionDescription);
}
```

doCall과 doAnswer를 통해서 Description을 교환하고 이 과정을 통해서 내 화상 정보가 상대방에게, 상대방의 화상정보가 내 뷰에 출력할 수 있게 되는 것이다.



```javascript
let pcConfig = {
    'iceServers': [{
        'urls': 'stun:stun.l.google.com:19302'
      }]
}

socket.on('message', (message)=>{
  console.log('Client received message :',message);
  if(message === 'got user media'){
    maybeStart();
  }else if(message.type === 'offer'){
    if(!isInitiator && !isStarted){
      maybeStart();
    }
    pc.setRemoteDescription(new RTCSessionDescription(message));
    doAnswer();
  }else if(message.type ==='answer' && isStarted){
    pc.setRemoteDescription(new RTCSessionDescription(message));
  }else if(message.type ==='candidate' &&isStarted){
    const candidate = new RTCIceCandidate({
      sdpMLineIndex : message.label,
      candidate:message.candidate
    });

    pc.addIceCandidate(candidate);
  }
})
```

위는 소켓통신에 대한 부분을 정의해서 데이터 교환을 올바르게 할 수 있게 해준다.



```javascript
const http = require('http');
const os = require('os');
const socketIO = require('socket.io');
const nodeStatic = require('node-static');

let fileServer = new(nodeStatic.Server)();
let app = http.createServer((req,res)=>{
    fileServer.serve(req,res);
}).listen(8080);

let io = socketIO.listen(app);
io.sockets.on('connection',socket=>{
    function log() {
        let array = ['Message from server:'];
        array.push.apply(array,arguments);
        socket.emit('log',array);
    }

    socket.on('message',message=>{
        log('Client said : ' ,message);
        socket.broadcast.emit('message',message);
    });

    socket.on('create or join',room=>{
        let clientsInRoom = io.sockets.adapter.rooms[room];
        let numClients = clientsInRoom ? Object.keys(clientsInRoom.sockets).length : 0;
        log('Room ' + room + ' now has ' + numClients + ' client(s)');
        
        if(numClients === 0){
            console.log('create room!');
            socket.join(room);
            log('Client ID ' + socket.id + ' created room ' + room);
            socket.emit('created',room,socket.id);
        }
        else if(numClients===1){
            console.log('join room!');
            log('Client Id' + socket.id + 'joined room' + room);
            io.sockets.in(room).emit('join',room);
            socket.join(room);
            socket.emit('joined',room,socket.id);
            io.sockets.in(room).emit('ready');
        }else{
            socket.emit('full',room);
        }
    });


});
```

위는 signaling 서버에 대한 구현으로 room이 없다면 생성하고, room이 이미 존재한다면 room에 참가하여 내 뷰를 상대방에게 중개해주는 그런 역할을 한다.