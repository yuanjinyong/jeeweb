<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <clothes-size-detail ref="detail" :detail-options="detailOptions"></clothes-size-detail>

    <partner-detail ref="partnerDetail" :detail-options="partnerDetailOptions"></partner-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'clothesSizeView',
    mixins: [ViewlMixin],
    components: {
      PartnerDetail: r => require.ensure([], () => r(require('views/admin/diy/partner/partner/Detail')), 'diy-partner-partner'),
      ClothesSizeDetail: r => require.ensure([], () => r(require('./Detail')), 'diy-partner-clothes-size')
    },
    data () {
      return {
        partnerDetailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        detailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '衣服尺码',
            url: 'api/diy/partner/clothes/sizes',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'convert(f_partner_name USING gbk), f_code'
            }
          }
        })
      }
    },
    computed: {
      curUser () {
        return this.$store.state.user || {}
      },
      permission () {
        return {
          add: this.hasPermission('DIY-HZHB-YFCM-ZJ'),
          edit: this.hasPermission('DIY-HZHB-YFCM-XG'),
          remove: this.hasPermission('DIY-HZHB-YFCM-SC')
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        type: ['IndexRender', this.mode !== 'selector' ? 'AddHeader' : 'Null']
      }, {
        headerName: '合作伙伴',
        field: 'f_partner_name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(f_partner_name USING gbk)',
        type: ['ViewRender', 'LikeFilter'],
        cellRendererParams: {
          operation: {
            title: '查看合作伙伴',
            onClick (params, entity) {
              params.context.featureComponent.$refs['partnerDetail'].open({
                operation: this.operation.id,
                title: this.operation.title,
                params: {f_id: entity.f_partner_id}
              })
            }
          }
        },
        width: 160
      }, {
        headerName: '编码',
        field: 'f_code',
        pinned: 'left',
        suppressSorting: false,
        type: ['ViewRender', 'LikeFilter'],
        width: 100
      }, {
        headerName: '名称',
        field: 'f_name',
        suppressSorting: false,
        sortColId: 'convert(f_name USING gbk)',
        type: ['LikeFilter'],
        width: 100
      }, {
        headerName: '类型',
        field: 'f_type',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'ClothesSizeType'},
        filterParams: {type: 'in', filterType: 'String'},
        width: 64
      }, {
        headerName: '尺码',
        field: 'f_size',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'ClothesSizeSize'},
        filterParams: {type: 'in', filterType: 'String'},
        width: 64
      }, {
        headerName: '描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        width: 300
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        width: 300
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              return entity.f_is_preset === 101
            }
          }]
        },
        width: 80
      }]
    }
  }
</script>
