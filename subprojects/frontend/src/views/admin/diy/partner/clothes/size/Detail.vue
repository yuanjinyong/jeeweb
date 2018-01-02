<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-2" label="合作伙伴名称" prop="f_partner_name">
        <jw-input-selector v-model="partner" :input-options="inputPartnerOptions">
          <partner-view ref="partnerView" :mode="'selector'"></partner-view>
        </jw-input-selector>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="类型" prop="f_type">
        <jw-dict v-model="entity.f_type" :dict-code="'ClothesSizeType'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="尺码" prop="f_type">
        <jw-dict v-model="entity.f_size" :dict-code="'ClothesSizeSize'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="编码" prop="f_code">
        <el-input v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="描述" prop="f_desc">
        <el-input v-model="entity.f_desc" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="f_remark">
        <el-input v-model="entity.f_remark" type="textarea" :autosize="{minRows: 10, maxRows: 20}"></el-input>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'clothesSizeDetail',
    mixins: [DetailMixin],
    components: {
      PartnerView: r => require.ensure([], () => r(require('views/admin/diy/partner/partner/View')), 'diy-partner-partner')
    },
    data () {
      return {
        inputPartnerOptions: {
          selectorOptions: {
            height: 400,
            context: {
              name: '合作伙伴',
              featureComponent: this,
              getViewComponent (options) {
                return options.context.featureComponent.$refs['partnerView']
              }
            }
          }
        },
        options: {
          context: {
            name: '衣服尺码',
            url: 'api/diy/partner/clothes/sizes'
          },
          createEntity (options, cb) {
            cb({
              f_partner_id: null,
              f_partner_name: null,
              f_type: 'A',
              f_size: 'L',
              f_code: 'L-A',
              f_name: '通用大码',
              f_desc: null,
              f_remark: null
            })
          }
        },
        entity: {},
        rules: {
          f_partner_name: [
            {required: true, message: '请选择伙伴', trigger: 'blur'}
          ],
          f_code: [
            {required: true, message: '请输入颜色编码', trigger: 'blur'},
            {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入颜色名称', trigger: 'blur'},
            {max: 50, message: '长度在50个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      partner: {
        get () {
          return {f_id: this.entity.f_partner_id, f_name: this.entity.f_partner_name}
        },
        set (partner) {
          this.entity.f_partner_id = partner.f_id
          this.entity.f_partner_name = partner.f_name
        }
      }
    }
  }
</script>
