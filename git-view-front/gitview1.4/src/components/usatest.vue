<<template>
  <div class= "usa_content">
    <div>
      <chart id="usaechart"></chart>
    </div>
  </div>
</template>

<<script>
import echarts from 'echarts'
export default {
  name: 'usatest',
  mounted () {
    this.$nextTick(function () {
      this.draw()
    })
  },
  data () {
    return {
      chart: null
    }
  },
  methods: {
    draw () {
      require(['echarts', 'echarts/src/util/mapData/params', 'echarts/src/chart/map']).params.USA = {
        getGeoJson: function (callback) {
          $.getJSON('../../node_modules/map/USA.json', callback);
        },
        specialArea : {
          Alaska : {              // 把阿拉斯加移到美国主大陆左下方
            left : -127,        // 安放位置起始点为西经113
            top : 30,           // 北纬31度
            width : 15,         // 区间大小宽高不大于15度
            height : 15
          },
          Hawaii : {
            left : -110,        // 夏威夷
            top : 28,
            width : 5,
            height : 5
          },
          'Puerto Rico' : {       // 波多黎各
            left : -76,
            top : 26,
            width : 2,
            height : 2
          }
        }
      }
      this.chart = echarts.init(document.getElementById('usaechart'))
      this.chart.setOption({
        width: '250%',
        height: '100%',
        title: {
          text: 'USA Population Estimates (2012)',
          subtext: 'Data from www.census.gov',
          sublink: 'http://www.census.gov/popest/data/datasets.html',
          left: 'right'
        },
        tooltip: {
          trigger: 'item',
          showDelay: 0,
          transitionDuration: 0.2,
          formatter: function (params) {
            var value = (params.value + '').split('.')
            value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
            return params.seriesName + '<br/>' + params.name + ': ' + value
          }
        },
        visualMap: {
          left: 'right',
          min: 500000,
          max: 38000000,
          inRange: {
            color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
          },
          text: ['High', 'Low'],           // 文本，默认为数值文本
          calculable: true
        },
        toolbox: {
          show: true,
          // orient: 'vertical',
          left: 'left',
          top: 'top',
          feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
          }
        },
        series: [
          {
            name: 'USA PopEstimates',
            type: 'map',
            roam: true,
            map: 'usa',
            left: 'cenetr',
            top: 'center',
            itemStyle: {
              emphasis: {label: {show: true}}
            },
            // 文本位置修正
            textFixed: {
              Alaska: [20, -20]
            },
            data: [
              {name: 'Alabama', value: 4822023},
              {name: 'Alaska', value: 731449},
              {name: 'Arizona', value: 6553255},
              {name: 'Arkansas', value: 2949131},
              {name: 'California', value: 38041430},
              {name: 'Kansas', value: 2885905},
              {name: 'Oklahoma', value: 3814820}
            ]
          }
        ]
      })
    }
  }
}
</script>
<<style>
.usa_content{
  background-color: #ccc;
}
#usaechart{
  height: 600px;
  width: 600px;
  margin-top:200px;
  background-color:#ccc;
}
</style>

