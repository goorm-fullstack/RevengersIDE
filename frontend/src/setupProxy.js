const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  {
    /** 확장자 .js 유지할 것 */
  }
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
      headers: { 'Content-Type': 'application/json; charset=UTF-8' },
    })
  );
};
