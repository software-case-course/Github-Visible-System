<template>
  <div class="main_content">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <div class="selector-container">
          <a v-on:click="worlddis" class="selector-box">全球分布</a>
        </div>
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
      </div>
    </transition>
    <transition name="fade">
      <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
    </transition>
    <div class="chart-container">
      <chart id="Echa" class="Echa" v-show="echaShow"></chart>
      <chart id="barEcha" class="barEcha" v-show="barEchaShow"></chart>
    </div>
  </div>
</template>

<script>
  import _ from 'lodash'
  import echarts from 'echarts'
  import { mapGetters, mapActions } from 'vuex'
  export default {
    name: 'first',
    mounted() {
      this.$nextTick(function () {
        this.getpiedata()
        this.getbardata()
      })
    },
    data() {
      return {
        chart: null,
        piedata: [],
        bardatalanguage: [],
        bardatarepo: [],
        bardatausers: [],
        id: null,
        echaShow: true,
        barEchaShow: true
      }
    },
    computed: {
    ...mapGetters(['shownav'])
    },
    methods: {
      worlddis() {
        this.setshow()
        this.$router.push({ path: '/worlddistribute' })
      },
      test() {
        console.log()
      },
      setshow() {
        this.changenav()
      },
    ...mapActions(['changenav']),
      async getpiedata() {
        this.piedata = []
        var data1 = []
        await this.$http.get('http://www.kongin.cn/git-view/search/support_languages').then(response => {
          // console.log(response)
          for (let i = 0; i < response.body.length; i++) {
            let res = response.body[i]
            var value = (res.repo)
            var name = (res.language)
            data1.push({ value, name })
          }
          this.piedata = _.cloneDeep(data1)
          // console.log(this.piedata)
          this.drawGraph()
        })
      },
      async getbardata() {
        this.bardatalanguage = []
        this.bardatarepo = []
        this.bardatausers = []
        await this.$http.get('http://www.kongin.cn/git-view/search/support_languages').then(response => {
          // console.log(response)
          for (let i = 0; i < response.body.length; i++) {
            let res = response.body[i]
            let value1 = _.cloneDeep(res.language)
            let value2 = _.cloneDeep(res.repo)
            let value3 = _.cloneDeep(res.users)
            this.bardatalanguage.push(value1)
            this.bardatarepo.push(value2)
            this.bardatausers.push(value3)
          }
          // console.log(this.bardatalanguage)
          // console.log(this.bardatarepo)
          // console.log(this.bardatausers)
          this.drawGraph()
        })
      },
      async drawGraph() {
        var mainColor = '#ccc'
        var textColor = "#222222"
        var self = this
        var pieChart = echarts.init(document.getElementById('Echa'))
        var barChart = echarts.init(document.getElementById('barEcha'))
        let pieOption = {
          baseOption: {
            backgroundColor: mainColor,
            title: {
              text: 'Github 占比最大前十语言',
              left: 'center',
              top: 0,
              textStyle: {
                color: textColor
              }
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            toolbox: {
              show: true,
              orient: ''
            },
            visualMap: {
              show: false,
              min: 100000,
              max: 1200000,
              color: textColor,
              inRange: {
                colorAlpha: 1,
              }
            },
            series: [
              {
                color: ['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6699CC', '#155fea', '#c4ccd3'],
                name: '语言及仓库数量：',
                type: 'pie',
                radius: '75%',
                center: ['50%', '50%'],
                data: this.piedata.sort(function (a, b) { return a.value - b.value }),
                roseType: 'angle',
                label: {
                  normal: {
                    textStyle: {
                      color: textColor
                    }
                  }
                },
                labelLine: {
                  normal: {
                    lineStyle: {
                      color: textColor
                    },
                    smooth: 0.2,
                    length: 10,
                    length2: 20
                  }
                },
                itemStyle: {
                  normal: {
                    shadowBlur: 20,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                },
                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                  return Math.random() * 200
                }
              }
            ]
          }
        }
        let barOption = {
          baseOption: {
            backgroundColor: mainColor,
            title: {
              left: 20,
              text: '仓库及用户数量',
              textStyle: {
                color: textColor
              }
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              orient: 'horizontal',
              textStyle: {
                color: textColor
              },
              data: ['仓库数量', '用户数量']
            },
            grid: {
              left: 80,
              right: 80
            },
            toolbox: {
              show: true,
              right: '50',
              feature: {
                dataView: { show: true, readOnly: false },
                magicType: { show: true, type: ['line', 'bar'] },
                restore: { show: true },
                saveAsImage: { show: true }
              }
            },
            calculable: true,
            xAxis: [
              {
                type: 'category',
                data: this.bardatalanguage,
                axisLine: {
                  show: true,
                  lineStyle: {
                    color: textColor
                  }
                },
                axisLabel: {
                  interval: 0,
                  rotate: 30,
                  textStyle: {
                    color: textColor
                  }
                }
              }
            ],
            yAxis: [
              {
                type: 'value',
                axisLine: {
                  show: true,
                  lineStyle: {
                    color: textColor
                  }
                },
                axisLabel: {
                  textStyle: {
                    color: textColor
                  }
                }
              }
            ],
            series: [
              {
                name: '仓库数量',
                type: 'bar',
                data: this.bardatarepo,
                markLine: {
                  data: [
                    { type: 'average', name: '平均值' }
                  ]
                },
                barGap: '-100%',
                itemStyle: {
                  normal: {
                    color: '#003366'
                  }
                }
              },
              {
                name: '用户数量',
                type: 'bar',
                itemStyle: {
                  normal: {
                    color: '#006699'
                  }
                },
                data: this.bardatausers,
                markLine: {
                  data: [
                    {
                      type: 'average',
                      name: '平均值'
                    }
                  ]
                }
              }
            ]
          },
          media: [
            {
              query: {
                maxWidth: 501
              },
              option: {
                toolbox: {
                  orient: 'vertical'
                },
                legend: {
                  orient: 'vertical'
                }
              }
            },
            {
              query: {
                minWidth: 501
              },
              option: {
                toolbox: {
                  orient: 'horizontal'
                },
                legend: {
                  orient: 'horizontal'
                }
              }
            }
          ]
        }
        window.onresize = function () {
          barChart.resize()
          pieChart.resize()
        }
        barChart.setOption(barOption)
        pieChart.setOption(pieOption)
        barChart.on('click', function (param) {
          console.log(param.name)
          self.$router.push({ path: '/language', query: { name: param.name } })
        })
        pieChart.on('click', function (param) {
          console.log(param.name)
          self.$router.push({ path: '/language', query: { name: param.name } })
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main_content{
    width: 100%;
    background-color: #ccc;
    overflow: hidden;
    padding-top: 60px;
    padding-bottom: 60px;
  }
  .chart-container{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
  }
  .Echa{
    height:500px;
    width:600px;
    margin-top: 100px;
  }
  .barEcha{
    height:500px;
    width:600px;
    margin-top: 100px;
  }
.selector-container{
  box-sizing: border-box;
  width: 100%;
  height: 134px;
  padding: 20px;
}
.selector-box{
  display: inline-block;
  line-height: 150px;
  width: 20%;
  height: 134px;
  font-size: 2rem;
  color: #fff;
  text-align: center;
  cursor: pointer;
  background-color: #222222;
  border-top: 10px solid transparent;
}
.selector-box:hover{
  border-top: 10px solid #155fea;
}
@media screen and (max-width: 1200px){
  .Echa{
    width: 500px;
  }
  .barEcha{
    width: 500px;
  }
}
@media screen and (max-width: 1000px){
  .Echa{
    width: 400px;
    height: 400px;
  }
  .barEcha{
    width: 400px;
    height: 400px;
  }
}
@media screen and (max-width:800px){
  .chart-container{
    flex-direction: column;
  }
}
@media screen and (max-height:700px){
  .Echa{
    height: 400px;
  }
  .barEcha{
    height: 400px;
  }
}
</style>