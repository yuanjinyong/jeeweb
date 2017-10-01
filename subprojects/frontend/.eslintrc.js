// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint', //解析器，这里我们使用babel-eslint
  parserOptions: {
    sourceType: 'module' //类型为module，因为代码使用了使用了ECMAScript模块
  },
  env: {
    browser: true, //预定义的全局变量，这里是浏览器环境
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard', //扩展，可以通过字符串或者一个数组来扩展规则
  // required to lint *.vue files
  plugins: [
    'html' //插件，此插件用于识别文件中的js代码，没有MIME类型标识没有script标签也可以识别到，因此拿来识别.vue文件中的js代码
  ],
  //这里写自定义规则
  'rules': {
    'import/first': 0,
    'standard/no-callback-literal': [0, ["cb", "callback"]],
    'no-multiple-empty-lines': 0, // 允许存在多个空行
    'camelcase': [0, {'properties': 'never'}], //不强制驼峰命名规则
    'no-multi-spaces': [2, {'ignoreEOLComments': true}], // 注释前允许多个空格
    'no-useless-return': 1, // 多余的return语句给出警告
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 1,
    'no-alert': process.env.NODE_ENV === 'production' ? 2 : 1
  }
}
