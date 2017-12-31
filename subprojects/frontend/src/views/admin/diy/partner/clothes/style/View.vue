<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <clothes-style-detail ref="detail" :detail-options="detailOptions"></clothes-style-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'clothesStyleView',
    mixins: [ViewlMixin],
    components: {
      ClothesStyleDetail: r => require.ensure([], () => r(require('./Detail')), 'diy-partner-clothes-style')
    },
    data () {
      return {
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
            name: '衣服款式',
            url: 'api/diy/partner/clothes/styles',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'convert(f_name USING gbk)'
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
          add: this.hasPermission('DIY-HZHB-YFKS-ZJ'),
          edit: this.hasPermission('DIY-HZHB-YFKS-XG'),
          remove: this.hasPermission('DIY-HZHB-YFKS-SC')
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
        type: ['LikeFilter'],
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
        width: 160
      }, {
        headerName: '人群类型',
        field: 'f_crowd_type',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'ClothesStyleCrowdType'},
        width: 120
      }, {
        headerName: '成本价(元)',
        field: 'f_cost_price',
        width: 100
      }, {
        headerName: '重量(g)',
        field: 'f_weight',
        width: 100
      }, {
        headerName: '创建人',
        field: 'f_creator_id',
        type: ['DictRender'],
        cellRendererParams: {dict: 'UserNameAll'},
        width: 100
      }, {
        headerName: '创建时间',
        field: 'f_created_time',
        suppressSorting: false,
        type: ['TimestampRender', 'TimestampFilter']
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
        headerName: '状态',
        field: 'f_status',
        pinned: 'right',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'ClothesStyleStatus'},
        width: 64
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
