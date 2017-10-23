<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <div class="jw-field jw-field-2">
        <jw-menu-selector ref="menuSelector" :style="{'overflow-y': 'auto', 'max-height': maxTreeHeight + 'px'}">
        </jw-menu-selector>
      </div>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'jwAuthorize',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '授权可以操作的功能',
            url: null,
            detailComponent: this
          },
          loadRemoteEntity (options, cb) {
            options.context.detailComponent._loadEntity(cb)
          },
          submitEntity (options, cb) {
            options.context.detailComponent._submitEntity(cb)
          }
        },
        entity: {}
      }
    },
    computed: {
      maxTreeHeight () {
        return this.$store.state.layout.middle.height - this.$store.state.dialog.header.height - this.$store.state.dialog.footer.height - 20 - 20
      }
    },
    methods: {
      _loadEntity (cb) {
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id + '/menus', {params: this.options.queryString}).then((response) => {
          let entity = {menuSelector: response.body.success ? response.body.data : []}
          cb(entity)
          this.$refs['menuSelector'].setData(entity.menuSelector)
        })
      },
      _submitEntity (cb) {
        let selectedMenuIds = this.$refs['menuSelector'].getCheckedKeys()
        this.$http.post(this.options.context.url + '/' + this.options.params.f_id + '/menus', {f_menu_ids: selectedMenuIds.join(',')}, {
          emulateJSON: true,
          params: this.options.queryString
        }).then((response) => {
          if (response.body.success) {
            cb(response.body.data)
          }
        })
      }
    }
  }
</script>
