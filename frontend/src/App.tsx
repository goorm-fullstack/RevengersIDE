import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { GlobalStyle } from './Style/GlobalStyle';
import * as dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/ko';

dayjs.extend(relativeTime);
dayjs.locale('ko');

function App() {
  return (
    <BrowserRouter>
      <GlobalStyle />
      <p>index</p>
    </BrowserRouter>
  );
}

export default App;
