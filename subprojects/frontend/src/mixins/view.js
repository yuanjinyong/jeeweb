/**
 * Created by 袁进勇 on 2017/6/12.
 */
export default {
  props: {
    mode: {type: String, default: ''},
    multiple: {type: Boolean, default: false}
  },
  data () {
    return {
      detailOptions: {
        size: this.mode === 'selector' ? 'mini' : 'small',
        modal: this.mode !== 'selector',
        maxHeight: this.mode === 'selector' ? 535 : null
      }
    }
  },
  computed: {
    permission () {
      return {
        add: false,
        edit: false,
        remove: false
      }
    },
    contentStyle () {
      let height = this.$store.state.layout.viewTab.body.height
      if (this.mode === 'selector') {
        height = this.$store.state.layout.window.height - 75 - 30 - 59 - 76 - 30 - 35
      }
      return {'padding': '20px', 'height': height + 'px'}
    }
  },
  created () {
    if (this.gridOptions) {
      this.gridOptions.rowSelection = this.multiple ? 'multiple' : 'single'
      this.gridOptions.columnDefs = []
    }
  },
  methods: {
    getSelectedRows () {
      if (this.gridOptions) {
        return this.gridOptions.api.getSelectedRows()
      } else {
        return this.multiple ? [] : null
      }
    },
    clearSelectedRows () {
      if (this.gridOptions) {
        this.gridOptions.api.deselectAll()
      }
    }
  }
}
