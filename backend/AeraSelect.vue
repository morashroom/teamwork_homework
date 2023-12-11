<template>
  <div class="aera-select">
    <el-cascader
        :options="options"
        v-model="selecteds"
        placeholder="请选择地区位置"
        :disabled="disabled"
    >
    </el-cascader>
  </div>
</template>

<script>
import { regionData } from 'element-china-area-data';
export default {
  name: 'AeraSelect',
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: Array,
    disabled: Boolean,
  },
  data() {
    return {
      options: regionData,
      selecteds: [],
    }
  },
  mounted() {
    this.selecteds = this.value;
  },
  watch: {
    selecteds(val) {
      this.$emit('change', val);
    }
  }
}
</script>

<style scoped lang="scss">
</style>
