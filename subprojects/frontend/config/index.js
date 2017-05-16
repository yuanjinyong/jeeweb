// see http://vuejs-templates.github.io/webpack for documentation.
var path = require('path')

var apiUrl = 'http://localhost:18080/zhuku' // 这里8080端口后的/zhuku为后台的contextPath。只在开发环境下使用
var assetsRoot = path.resolve(__dirname, '../build/webapp')
var assetsSubDirectory = 'assets'
var assetsPublicPath = ''

//set-cookie:JSESSIONID=5FCC77E2B97AF98269FFFEF373F83C8C;path=/zhuku;HttpOnly
//set-cookie:JSESSIONID=5FCC77E2B97AF98269FFFEF373F83C8C;path=/;HttpOnly
var setCookie = function (proxyRes, req, res) {
  var oldCookie = proxyRes.headers['set-cookie']
  if (oldCookie == null || oldCookie.length == 0) {
    delete proxyRes.headers['set-cookie']
    return
  }

  var oldCookieItems = oldCookie[0].split(';')
  var newCookie = ''
  for (var i = 0; i < oldCookieItems.length; ++i) {
    if (newCookie.length > 0)
      newCookie += ';'
    if (oldCookieItems[i].indexOf('path=') == 0) {
      newCookie += 'path=/' + assetsPublicPath
    } else {
      newCookie += oldCookieItems[i]
    }
  }

  proxyRes.headers['set-cookie'] = [newCookie]
}

module.exports = {
  build: {
    env: require('./prod.env'),
    title: '筑库网项目管理系统',
    index: path.resolve(assetsRoot, 'index.html'),
    assetsRoot: assetsRoot,
    assetsSubDirectory: assetsSubDirectory,
    assetsPublicPath: assetsPublicPath,
    productionSourceMap: true,
    // Gzip off by default as many popular static hosts such as Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to: npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],
    // Run the build command with an extra argument to View the bundle analyzer report after build finishes: `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  },
  dev: {
    env: require('./dev.env'),
    port: 3000,
    autoOpenBrowser: true,
    assetsSubDirectory: assetsSubDirectory,
    assetsPublicPath: assetsPublicPath,
    proxyTable: {
      // '/api/**': {
      //   target: apiUrl,
      //   onProxyRes (proxyRes, req, res) {
      //     setCookie(proxyRes, req, res)
      //   }
      // }
    },
    // CSS Sourcemaps off by default because relative paths are "buggy"
    // with this option, according to the CSS-Loader README
    // (https://github.com/webpack/css-loader#sourcemaps)
    // In our experience, they generally work as expected,
    // just be aware of this issue when enabling this option.
    cssSourceMap: false
  }
}
