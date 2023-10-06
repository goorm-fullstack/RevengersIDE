import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Chat from '../Chat/Chat';

const Footer = () => {
  const [chat, setToggleChat] = useState(false);
  const toggleChat = () => {
    setToggleChat((prev) => !prev);
  };

  return (
    <S.Footer>
      <div className="left">
        <Link to="/admin" style={{ color: 'white' }}>관리자 페이지</Link>
      </div>
      <div className="right">
        <button type="button" onClick={toggleChat} data-isactive={chat}>
          <svg id="Layer_1" version="1.1" viewBox="0 0 128 128" xmlns="http://www.w3.org/2000/svg">
            <g>
              <polygon points="26.7,107 103,107 103,99 23.3,99 9,114 9,25 1,25 1,127 7.7,127  " />
              <path d="M21,87h79.3l20,20h6.7V1H21V87z M29,9h90v85.3L103.7,79H29V9z" />
              <rect height="8" width="58" x="45" y="31" />
              <rect height="8" width="58" x="45" y="49" />
            </g>
          </svg>
        </button>
        <div className="chatcontainer" data-isactive={chat}>
          <Chat />
        </div>
      </div>
    </S.Footer>
  );
};

export default Footer;
