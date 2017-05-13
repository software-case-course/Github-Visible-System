<template>
<div class="world_content">
  <transition name="slide">
    <div v-show="this.shownav" class="information-box">
      <!--<form>
      </form>-->
      <!--<div class="user-head-picture-box"></div>-->
      <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow"/>
    </div>
  </transition>
  <transition name="fade">
    <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
  </transition>
  <div>
    <chart id="mapchart"></chart>
  </div>
  <div class="backbutton">
    <img src="../assets/images/ic_arrow_back_white_36dp_1x.png" @click="toworld">
  </div>
</div>
</template>

<script>
import _ from 'lodash'
import echarts from 'echarts'
import { mapGetters, mapActions } from 'vuex'
// import worldJson from '../../node_modules/map/world.json'
export default {
  name: 'worlddis',
  mounted () {
    this.$nextTick(function () {
      var ndate = new Date()
      ndate.toLocaleDateString()
      this.year = ndate.getFullYear()
      this.getdisdata()
    })
  },
  computed: {
    ...mapGetters(['shownav'])
  },
  data () {
    return {
      id: 'mapchart',
      worlddata: [],
      usadata: [],
      chinadata: [],
      chart: null,
      year: '',
      maxnum: '',
      option: {
        width: '250%',
        title: {
          text: 'github 用户分布情况',
          subtext: '',
          x: 'center',
          y: 'top'
        },
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            var value = (params.value + '').split('.')
            return params.seriesName + '<br/>' + params.name + ' : ' + value
          }
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          x: 'right',
          y: 'center',
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        dataRange: {
          min: 0,
          max: 20000,
          text: ['High', 'Low'],
          realtime: false,
          calculable: true,
          color: ['green', 'yellow', 'blue']
        },
        series: [
          {
            width: '75%',
            height: '75%',
            left: 'cenetr',
            top: 'center',
            name: 'github用户全球分布图',
            type: 'map',
            mapType: 'world',
            roam: true,
            mapLocation: {
              y: 60
            },
            itemStyle: {
              emphasis: {label: {show: true}}
            },
            data: []
          }
        ]
      }
    }
  },
  methods: {
    ...mapActions(['changenav']),
    setshow () {
      this.changenav()
    },
    async getdisdata () {
      var data1 = []
      var data2 = []
      var data3 = []
      await this.$http.get('http://www.kongin.cn/git-view/search/country').then(response => { // 获取全球数据
        // console.log(response)
        for (let i = 0; i < response.body.length; i++) {
          let res = response.body[i]
          var name = this.countryfilter(res.country)
          var value = res.users
          // console.log(name)
          data1.push({name, value})
        }
      })
      await this.$http.get('http://www.kongin.cn/git-view/search/country?country=USA').then(response => {
        for (let i = 0; i < response.body.length; i++) {
          let res = response.body[i]
          var name = this.usacityfilter(res.location)
          var value = res.users
          data2.push({ name, value })
        }
      })
      await this.$http.get('http://www.kongin.cn/git-view/search/country?country=China').then(response => {
        for (let i = 0; i < response.body.length; i++) {
          let res = response.body[i]
          var name = this.chinacityfilter(res.location)
          var value = res.users
          // console.log(name)
          data3.push({name, value})
        }
      })
      this.worlddata = _.cloneDeep(data1)
      this.usadata = _.cloneDeep(data2)
      this.chinadata = _.cloneDeep(data3)
      // console.log(this.chinadata)
      // console.log(this.usadata)
      // console.log(this.worlddata)
      this.drawMap(this.id)
    },
    drawMap (id) {
      this.chart = echarts.init(document.getElementById(id))
      for (let i = 0; i < this.worlddata.length; i++) {
        var item = this.worlddata[i]
        this.option.series[0].data.push({name: item.name, value: item.value})
      }
      this.chart.setOption(this.option)
      this.chart.on('click', this.chartclick)
    },
    toworld () {
      this.chart.setOption(this.option, false)
      this.option.title.text = 'github用户全球分布图'
      Object.assign(this.option.series[0], {
        mapType: 'world',
        width: '75%',
        height: '75%'
      })
      this.chart.setOption(this.option, true)
    },
    chartclick (param) {
      // console.log(param)
      this.chart.setOption(this.option, false)
      switch (param.data.name) {
        case 'United States of America':
          for (let i = 0; i < this.usadata.length; i++) {
            var item = this.usadata[i]
            this.option.series[0].data.push({name: item.name, value: item.value})
          }
          this.option.title.text = 'github用户美国分布图'
          Object.assign(this.option.series[0], {
            mapType: 'usa',
            width: '275%',
            height: '100%'
          })
          this.chart.setOption(this.option, true)
          break
        case 'China':
          for (let i = 0; i < this.chinadata.length; i++) {
            var item1 = this.chinadata[i]
            this.option.series[0].data.push({name: item1.name, value: item1.value})
          }
          this.option.title.text = 'github用户中国分布图'
          Object.assign(this.option.series[0], {
            mapType: 'china',
            width: '100%',
            height: '100%'
          })
          this.chart.setOption(this.option, true)
          break
      }
    },
    chinacityfilter (cname) {
      var cityname = ''
      switch (cname) {
        case 'Shanxi': cityname = '山西'; break
        case 'Guangdong': cityname = '广东'; break
        case 'Neimenggu': cityname = '内蒙古'; break
        case 'Fujian': cityname = '福建'; break
        case 'Tianjin': cityname = '天津'; break
        case 'Chongqing': cityname = '重庆'; break
        case 'Hebei': cityname = '河北'; break
        case 'Beijing': cityname = '北京'; break
        case 'Jiangsu': cityname = '江苏'; break
        case 'Yunnan': cityname = '云南'; break
        case 'Henan': cityname = '河南'; break
        case 'Shanghai': cityname = '上海'; break
        case 'Xinjiang': cityname = '新疆'; break
        case 'Heilongjijang': cityname = '黑龙江'; break
        case 'Liaoling': cityname = '辽宁'; break
        case 'Zhejiang': cityname = '浙江'; break
        case 'Shandong': cityname = '山东'; break
        case 'Anhui': cityname = '安徽'; break
        case 'Hunan': cityname = '湖南'; break
        case 'Guangxi': cityname = '广西'; break
        case 'Gansu': cityname = '甘肃'; break
        case 'Jilin': cityname = '吉林'; break
        case 'Jiangxi': cityname = '江西'; break
        case 'Hubei': cityname = '湖北'; break
        case 'Sichuan': cityname = '四川'; break
        case 'Hong Kong': cityname = '香港'; break
        case 'Macau': cityname = '澳门'; break
        case 'Taiwan': cityname = '台湾'; break
        case 'Qinghai': cityname = '青海'; break
        case 'Guangzhou': cityname = '广东'; break
        case 'Hainan': cityname = '海南'; break
        case 'Guizhou': cityname = '贵州'; break
        case 'Tibet': cityname = '西藏'; break
        case 'Ningxia': cityname = '宁夏'; break
        default: cityname = cname
      }
      return cityname
    },
    usacityfilter (cname) {
      var cityname = ''
      switch (cname) {
        default: cityname = cname
      }
      return cityname
    },
    countryfilter (cname) {
      var country = ''
      switch (cname) {
        case 'USA': country = 'United States of America'; break
        case 'UK': country = 'United Kingdom'; break
        case 'United States': country = 'United States of America'; break
        default: country = cname
      }
      return country
    }
  }
}
</script>
<style scoped>
.world_content{

}
#mapchart{
}
</style>
