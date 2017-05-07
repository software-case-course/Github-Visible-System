<template>
<div class="warelist_content">
  <div>
    <!--<ul v-for="(item, index) in waredata">
      <a v-html="item. name" v-bind:href="item.value"></a>
    </ul>-->
    <chart id="f"></chart>
  </div>
</div>
</template>
<script>
// import _ from 'lodash'
import echarts from 'echarts'
export default {
  name: 'warelist',
  data () {
    return {
      // waredata: []
      chart: null
    }
  },
  mounted () {
    this.$nextTick(function () {
      // this.getbackdata()
      this.draw('f')
    })
  },
  methods: {
    // async getbackdata () {
    //   var data1 = []
    //   var  name = 'baidu'
    //   var value = 'https://www.baidu.com'
    //   data1.push({ name, value})
    //    name = 'youku'
    //   value = 'https://www.youku.com'
    //   data1.push({ name, value})
    //   this.waredata = _.cloneDeep(data1)
    // }
    async draw (id) {
      this.chart = echarts.init(document.getElementById(id))
      this.chart.setOption({
        title: {
          text: 'iphone销量',
          subtext: '纯属虚构',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['iphone3', 'iphone4', 'iphone5']
        },
        visualMap: {
          min: 0,
          max: 2500,
          left: 'left',
          top: 'bottom',
          text: ['高', '低'],           // 文本，默认为数值文本
          calculable: true
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
          }
        },
        series: [
          {
            name: 'iphone3',
            type: 'map',
            mapType: 'china',
            roam: false,
            label: {
              normal: {
                show: true
              },
              emphasis: {
                show: true
              }
            },
            data: [
              { name: '北京', value: this.randomData() },
              { name: '天津', value: this.randomData() },
              { name: '上海', value: this.randomData() },
              { name: '重庆', value: this.randomData() },
              { name: '河北', value: this.randomData() },
              { name: '河南', value: this.randomData() },
              { name: '云南', value: this.randomData() },
              { name: '辽宁', value: this.randomData() },
              { name: '黑龙江', value: this.randomData() },
              { name: '湖南', value: this.randomData() },
              { name: '安徽', value: this.randomData() },
              { name: '山东', value: this.randomData() },
              { name: '新疆', value: this.randomData() },
              { name: '江苏', value: this.randomData() },
              { name: '浙江', value: this.randomData() },
              { name: '江西', value: this.randomData() },
              { name: '湖北', value: this.randomData() },
              { name: '广西', value: this.randomData() },
              { name: '甘肃', value: this.randomData() },
              { name: '山西', value: this.randomData() },
              { name: '内蒙古', value: this.randomData() },
              { name: '陕西', value: this.randomData() },
              { name: '吉林', value: this.randomData() },
              { name: '福建', value: this.randomData() },
              { name: '贵州', value: this.randomData() },
              { name: '广东', value: this.randomData() },
              { name: '青海', value: this.randomData() },
              { name: '西藏', value: this.randomData() },
              { name: '四川', value: this.randomData() },
              { name: '宁夏', value: this.randomData() },
              { name: '海南', value: this.randomData() },
              { name: '台湾', value: this.randomData() },
              { name: '香港', value: this.randomData() },
              { name: '澳门', value: this.randomData() }
            ]
          },
          {
            name: 'iphone4',
            type: 'map',
            mapType: 'china',
            label: {
              normal: {
                show: true
              },
              emphasis: {
                show: true
              }
            },
            data: [
              { name: '北京', value: this.randomData() },
              { name: '天津', value: this.randomData() },
              { name: '上海', value: this.randomData() },
              { name: '重庆', value: this.randomData() },
              { name: '河北', value: this.randomData() },
              { name: '安徽', value: this.randomData() },
              { name: '新疆', value: this.randomData() },
              { name: '浙江', value: this.randomData() },
              { name: '江西', value: this.randomData() },
              { name: '山西', value: this.randomData() },
              { name: '内蒙古', value: this.randomData() },
              { name: '吉林', value: this.randomData() },
              { name: '福建', value: this.randomData() },
              { name: '广东', value: this.randomData() },
              { name: '西藏', value: this.randomData() },
              { name: '四川', value: this.randomData() },
              { name: '宁夏', value: this.randomData() },
              { name: '香港', value: this.randomData() },
              { name: '澳门', value: this.randomData() }
            ]
          },
          {
            name: 'iphone5',
            type: 'map',
            mapType: 'china',
            label: {
              normal: {
                show: true
              },
              emphasis: {
                show: true
              }
            },
            data: [
              { name: '北京', value: this.randomData() },
              { name: '天津', value: this.randomData() },
              { name: '上海', value: this.randomData() },
              { name: '广东', value: this.randomData() },
              { name: '台湾', value: this.randomData() },
              { name: '香港', value: this.randomData() },
              { name: '澳门', value: this.randomData() }
            ]
          }
        ]
      })
    },
    randomData () {
      return Math.round(Math.random() * 1000)
    }

  }
}
</script>

<style scoped>
#f{
  weight:600px
}
</style>
