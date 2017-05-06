<script type="text/ecmascript-6">
  import Vue from 'vue'

  Vue.filter('dict', function (value, dict) {
    if (typeof (dict) === 'object') {
      if (typeof (dict.length) !== 'undefined') {
        for (var i = 0; i < dict.length; i++) {
          var dictItem = dict[i]
          if (dictItem.f_item_code === value + '') {
            return dictItem.f_item_text
          }
        }
      } else {
        return dict[value]
      }
    }

    return value
  })
</script>
