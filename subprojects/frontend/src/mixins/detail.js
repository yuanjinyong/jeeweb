/**
 * Created by 袁进勇 on 2017/6/12.
 */
export default {
  props: {
    detailOptions: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    return {
      entity: {}
    }
  },
  mounted () {
    this.$refs['form'].bus.$on('loaded-entity', (entity) => {
      this.entity = entity
    })
  },
  beforeDestroy () {
    this.$refs['form'].bus.$off('loaded-entity')
  },
  methods: {
    close () { // 供外部调用的接口
      this.$refs['form'].close()
    },
    open (options) { // 供外部调用的接口
      this.$lodash.merge(this.options, this.detailOptions, options)
      this.$refs['form'].open(this.options)
    }
  }
}
