const { createProxyMiddleware } = require('http-proxy-middleware');

// 확장자 .js 유지할 것
module.exports = function (app) {
  app.use(
    createProxyMiddleware('/api', {
      target: 'http://localhost:8080',
      changeOrigin: true,
    })
  );
};
