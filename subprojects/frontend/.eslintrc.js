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
    'import/first': 0,
    'standard/no-callback-literal': [0, ["cb", "callback"]],
    'no-multiple-empty-lines': 0, // ������ڶ������
    'camelcase': [0, {'properties': 'never'}], //��ǿ���շ���������
    'no-multi-spaces': [2, {'ignoreEOLComments': true}], // ע��ǰ�������ո�
    'no-useless-return': 1, // �����return����������
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 1,
    'no-alert': process.env.NODE_ENV === 'production' ? 2 : 1
  }
}
