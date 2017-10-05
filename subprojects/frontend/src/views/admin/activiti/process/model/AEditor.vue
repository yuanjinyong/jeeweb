<template>
  <el-dialog v-model="visible" :title="options.title" :size="'full'">
    <iframe :src="modelUrl"></iframe>
  </el-dialog>
</template>


<script>
  export default {
    name: 'processModelEditor',
    data () {
      return {
        visible: false,
        options: {
          title: '编辑流程',
          params: {},
          queryString: {}
        }
      }
    },
    computed: {
      modelUrl () {
        return this.$cfg.apiUrl + 'modeler.html?view=true&modelId=' + this.options.params.f_id
      }
    },
    methods: {
      close () { // 供外部调用的接口
        this.visible = false
      },
      open (options) { // 供外部调用的接口
        this.options.params = {}
        this.$lodash.merge(this.options, options)
        this.visible = true
      }
    }
  }
</script>
