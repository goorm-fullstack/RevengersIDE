import React, {useEffect, useRef, useState} from 'react';
import * as S from './Style';

type MessageType = 'TALK' | 'ENTER';

interface Message {
  type: MessageType;
  sender: string;
  message: string;
}

const Chat = () => {
  const wsURL = 'ws://localhost:8080/ws/chat';
  const ws = useRef<WebSocket | null>(null);
  const [wsConnected, setWsConnected] = useState(false);
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState('');
  const [username, setUsername] = useState("");

  // 엔터 입력 시 전송, 쉬프트+엔터 시 다음 줄
  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  // 메세지 삭제 (클라이언트 한정)
  const deleteMessage = (index: number) => {
    setMessages(prevMessages => prevMessages.filter((_, i) => i !== index));
  };

  // WebSocket 연결 설정
  useEffect(() => {
    ws.current = new WebSocket(wsURL);
    ws.current.onopen = () => {
      console.log('채팅(웹소켓)에 연결합니다.');
      setWsConnected(true);
    };
    ws.current.onmessage = (message) => {
      const parsedMessage: Message = JSON.parse(message.data);

      if (parsedMessage.sender === "SERVER" && parsedMessage.type === "ENTER") {
        setUsername(parsedMessage.message);
        return;
      }

      setMessages((prevMessages) => [...prevMessages, parsedMessage]);
    };
    ws.current.onclose = () => {
      console.log('채팅(웹소켓) 연결 해제합니다.');
      setWsConnected(false);
      setTimeout(() => {
        console.log('재연결 시도...');
        ws.current = new WebSocket(wsURL);
      }, 3000);
    };
  }, []);

  const sendMessage = () => {
    if (wsConnected && ws.current) {
      const message: Message = {
        type: 'TALK',
        sender: username,
        message: inputMessage,
      };
      ws.current.send(JSON.stringify(message));
      setInputMessage('');
    }
  };

  return (
    <S.Chat>
      <h3>
        Chats <span>{messages.length}</span>
      </h3>
      <ul className="messagew">
        {messages.map((message, index) => (
            <li key={index} className={message.type === 'ENTER' ? 'enter' : ''}>
              <strong>{message.sender}: </strong>
              <span>{message.message}</span>
            </li>
        ))}
      </ul>
      <div className="writewrapper">
        <div className="tab">
          <button type="button">
            {/* SVG 삭제 버튼 */}
          </button>
        </div>
        <div className="textareaw">
          <textarea
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyDown={handleKeyDown}
          ></textarea>
        </div>
        <button onClick={sendMessage}>Send</button>
      </div>
    </S.Chat>
  );
};

export default Chat;
