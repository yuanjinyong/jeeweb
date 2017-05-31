<style scoped>
</style>

<template>
  <el-select :ref="'input'" v-model="text" clearable>
    <el-option v-for="item in options" :key="item.f_item_code" :label="item.f_item_text"
      :value="item.f_item_code"></el-option>
  </el-select>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        text: '',
        valueGetter: null,
        options: []
      }
    },
    watch: {
      'text': function (val, oldVal) {
        if (val !== oldVal) {
          this.params.filterChangedCallback()
        }
      }
    },
    created () {
      this.valueGetter = this.params.valueGetter
      if (typeof (this.params.dict) === 'object') {
        if (typeof (this.params.dict.length) !== 'undefined') {
          this.options = this.params.dict
        } else {
          this.options = []
        }
      } else if (typeof (this.params.dict) === 'string') {
        this.options = Vue.store.state.dicts[this.params.dict]
      } else {
        this.options = []
      }
    },
    methods: {
      isFilterActive () {
        return this.text !== undefined && this.text !== null && this.text !== ''
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.valueGetter(params.node) === this.text
      },
      getModel () {
        return {filter: this.text, filterType: 'text', type: 'equals'}
      },
      setModel (model) {
        this.text = model.filter
      },
      afterGuiAttached () {
        // console.debug('afterGuiAttached', this.$refs.input)
        // this.$refs.input.focus()
      }
    }
  })
</script>
