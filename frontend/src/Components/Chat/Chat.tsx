import React, {useEffect, useRef, useState} from 'react';
import * as S from './Style';

type MessageType = 'TALK' | 'ENTER';

interface Message {
  type: MessageType;
  sender: string;
  message: string;
}

const Chat = () => {
  const wsURL = 'ws://3.34.230.219:8080/ideApi/ws/chat';
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
      const parsedMessage = JSON.parse(message.data);
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
  };

  useEffect(() => {
    connectWebSoket();

    // Cleanup 함수: 컴포넌트가 언마운트될 때 실행
    return () => {
      ws.current?.close();
    };
  }, []);  // 빈 배열을 dependency로 설정

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

  const [searchwrap, setToggleSearch] = useState(false);
  const toggleSearch = () => {
    setToggleSearch((prev) => !prev);
  };

  return (
      <S.Chat>
        <h3>
          <p>
            Chats <span>{messages.length}</span>
          </p>
          <button type="button" onClick={toggleSearch} data-isactive={searchwrap} className="searchbtn">
            <svg id="Layer_1" version="1.1" viewBox="0 0 50 50">
              <rect fill="none" height="50" width="50" />
              <circle cx="21" cy="20" fill="none" r="16" stroke-linecap="round" stroke-miterlimit="10" stroke-width="2" />
              <line fill="none" stroke-miterlimit="10" stroke-width="4" x1="32.229" x2="45.5" y1="32.229" y2="45.5" />
            </svg>
          </button>
          <div className="searchwrap" data-isactive={searchwrap}>
            <input type="text" placeholder="Search..." onChange={(e) => searchMessage(e.target.value)} />
          </div>
        </h3>
        <ul className="messagew">
          {messages.map((message, index) => (
              <li ref={(el) => (messageRefs.current[index] = el)} key={index} className={message.type === 'ENTER' ? 'enter' : ''}>
                <strong>{message.sender}: </strong>
                <span
                    dangerouslySetInnerHTML={{
                      __html: highlightText ? message.message.replace(new RegExp(`(${highlightText})`, 'gi'), '<mark>$1</mark>') : message.message,
                    }}
                />
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
            <textarea value={inputMessage} onChange={(e) => setInputMessage(e.target.value)} onKeyDown={handleKeyDown}></textarea>
          </div>
          <button onClick={sendMessage}>Send</button>
        </div>
      </S.Chat>
  );
};
export default Chat;
