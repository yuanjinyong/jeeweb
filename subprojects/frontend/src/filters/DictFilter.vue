<script type="text/ecmascript-6">
  import Vue from 'vue'

  Vue.filter('dict', function (value, dict) {
    var items = dict
    if (typeof (dict) === 'string') {
      items = Vue.store.state.dicts[dict]
    }

    if (typeof (items) === 'object') {
      if (typeof (items.length) !== 'undefined') {
        for (var i = 0; i < items.length; i++) {
          var item = items[i]
          if (item.f_item_code === value + '') {
            return item.f_item_text
          }
        }
      } else {
        return items[value]
      }
    }

    return value
  })
</script>
