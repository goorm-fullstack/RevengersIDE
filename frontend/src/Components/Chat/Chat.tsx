import React, {useEffect, useRef, useState} from 'react';
import * as S from './Style';

type MessageType = 'TALK' | 'ENTER';

interface Message {
  type: MessageType;
  sender: string;
  message: string;
}

const Chat = () => {
  const wsURL = 'wss://3.34.230.219/ideApi/ws/chat';
  const ws = useRef<WebSocket | null>(null);
  const [wsConnected, setWsConnected] = useState(false);
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState('');
  const [username, setUsername] = useState("");
  const messagesEndRef = useRef<null | HTMLDivElement>(null); // 스크롤을 최신채팅으로
  const messageRefs = useRef<{ [key: number]: HTMLLIElement | null }>({}); //
  const [highlightText, setHighlightText] = useState<string | null>(null);


  // 엔터 입력 시 전송, 쉬프트+엔터 시 다음 줄
  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  // 검색
  const searchMessage = (searchText: string) => {
    const foundIndex = messages.findIndex(message => message.message.includes(searchText));

    if (foundIndex !== -1 && messageRefs.current[foundIndex]) {
      messageRefs.current[foundIndex]?.scrollIntoView({ behavior: 'smooth' });
      setHighlightText(searchText);
    }
  };

  // 메세지 삭제 (클라이언트 한정)
  const deleteAllMessages = () => {
    setMessages([]);
  };

  // WebSocket 연결 설정
  useEffect(() => {
    const connectWebSoket = () => {
      ws.current = new WebSocket(wsURL);
      ws.current.onopen = () => {
        console.log('채팅(웹소켓)에 연결합니다.');
        setWsConnected(true);
        ws.current?.send(
            JSON.stringify({
              type: 'ENTER',
              sender: username,
              message: '입장',
            })
        );
        console.log('웹소켓 상태:', ws.current?.readyState);
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
        ws.current = null;
        setTimeout(() => {
          console.log('재연결 시도...');
          connectWebSoket();
        }, 3000);
      };
    }
    connectWebSoket();
  }, []);

  // 스크롤
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

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
        Chats <span></span>
        <input type="text" placeholder="Search..." onChange={(e) => searchMessage(e.target.value)} />
      </h3>
      <ul className="messagew">
        {messages.map((message, index) => (
            <li
                ref={(el) => messageRefs.current[index] = el}
                key={index}
                className={message.type === 'ENTER' ? 'enter' : ''}
            >
              <strong>{message.sender}: </strong>
              <span dangerouslySetInnerHTML={{
                __html: highlightText
                    ? message.message.replace(new RegExp(`(${highlightText})`, 'gi'), '<mark>$1</mark>')
                    : message.message
              }} />
            </li>
        ))}
        <div ref={messagesEndRef}></div>
      </ul>


      <div className="writewrapper">
        <div className="tab">
          {/** 메시지 삭제 버튼 */}
          <button type="button" onClick={deleteAllMessages}>
            <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
              <rect fill="none" height="256" width="256" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="216" x2="40" y1="56" y2="56" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="104" x2="104" y1="104" y2="168" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="152" x2="152" y1="104" y2="168" />
              <path d="M200,56V208a8,8,0,0,1-8,8H64a8,8,0,0,1-8-8V56" fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" />
              <path
                  d="M168,56V40a16,16,0,0,0-16-16H104A16,16,0,0,0,88,40V56"
                  fill="none"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="16"
              />
            </svg>
          </button>
        </div>
        <div className="textareaw">
          <textarea
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyDown={handleKeyDown}
          ></textarea>
        </div>
      </div>
    </S.Chat>
  );
};

export default Chat;
