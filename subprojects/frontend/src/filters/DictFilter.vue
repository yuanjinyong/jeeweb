<script type="text/ecmascript-6">
  import Vue from 'vue'

  Vue.filter('dict', function (value, dict) {
    return Vue.prototype.getDictValue(value, dict)
  })
</script>
