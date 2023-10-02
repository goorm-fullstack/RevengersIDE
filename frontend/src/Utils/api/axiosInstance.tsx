import axios from "axios";

const Instance = axios.create({
  baseURL : "http://127.0.0.1:8080",
  headers : {
    'Access-Control-Allow-Origin': '*', // 서버 domain
    'Access-Control-Allow-Credentials':"true",
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type, Authorization, Content-Length, X-Requested-With'
  }
});

export default Instance;