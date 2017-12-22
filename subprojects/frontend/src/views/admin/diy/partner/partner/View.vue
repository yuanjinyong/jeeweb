<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <partner-detail ref="detail" :detail-options="detailOptions"></partner-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'partnerView',
    mixins: [ViewlMixin],
    components: {
      PartnerDetail: r => require.ensure([], () => r(require('./Detail')), 'diy-partner-partner')
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
            name: '合作伙伴',
            url: 'api/diy/partner/partners',
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
          add: this.hasPermission('DIY-HZHB-HZHB-ZJ'),
          edit: this.hasPermission('DIY-HZHB-HZHB-XG'),
          remove: this.hasPermission('DIY-HZHB-HZHB-SC')
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
        headerName: '名称',
        field: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(f_name USING gbk)',
        type: ['ViewRender', 'LikeFilter'],
        width: 160
      }, {
        headerName: '负责人姓名',
        field: 'f_leader',
        tooltipField: 'f_leader',
        suppressSorting: false,
        sortColId: 'convert(f_leader USING gbk)',
        type: ['LikeFilter'],
        width: 100
      }, {
        headerName: '固定电话',
        field: 'f_phone',
        tooltipField: 'f_phone',
        suppressSorting: false,
        type: ['LikeFilter'],
        width: 100
      }, {
        headerName: '手机号码',
        field: 'f_mobile',
        tooltipField: 'f_mobile',
        suppressSorting: false,
        type: ['LikeFilter'],
        width: 100
      }, {
        headerName: '地址',
        field: 'f_address',
        tooltipField: 'f_address',
        type: ['LikeFilter'],
        width: 200
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
        cellRendererParams: {
          options: {
            format: 'YYYY年MM月DD日HH时mm分ss秒'
          }
        },
        type: ['TimestampRender', 'TimestampFilter']
      }, {
        headerName: '描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        width: 300
      }, {
        headerName: '状态',
        field: 'f_status',
        pinned: 'right',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'TrueFalse'},
        width: 64
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
