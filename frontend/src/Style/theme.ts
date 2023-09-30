import { DefaultTheme } from 'styled-components';

export const lightTheme: DefaultTheme = {
  bgColor: '#f0f2f5',
  bgPanel: '#fafafa',
  textColor: 'rgba(0,0,0,0.87)',
  borderColor: '#e8e8e8',
};

export const darkTheme: DefaultTheme = {
  bgColor: '#121212',
  bgPanel: '#222',
  textColor: 'rgba(255,255,255,0.87)',
  borderColor: 'rgba(255,255,255,0.09)',
};

export const theme = {
  lightTheme,
  darkTheme,
};
