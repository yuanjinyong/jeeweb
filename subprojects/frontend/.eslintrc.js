// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint', //����������������ʹ��babel-eslint
  parserOptions: {
    sourceType: 'module' //����Ϊmodule����Ϊ����ʹ����ʹ����ECMAScriptģ��
  },
  env: {
    browser: true, //Ԥ�����ȫ�ֱ��������������������
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard', //��չ������ͨ���ַ�������һ����������չ����
  // required to lint *.vue files
  plugins: [
    'html' //������˲������ʶ���ļ��е�js���룬û��MIME���ͱ�ʶû��script��ǩҲ����ʶ�𵽣��������ʶ��.vue�ļ��е�js����
  ],
  //����д�Զ������
  'rules': {
    // allow paren-less arrow functions
    'arrow-parens': 0,
    'camelcase': [0, {'properties': 'never'}], //��ǿ���շ���������
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0
  }
}
