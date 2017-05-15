<template>
  <div class="main_content">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <div class="selector-container" v-show="this.shownav">
          <a v-on:click="worlddis" class="selector-box">全球分布</a>
        </div>
          <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
      </div>
    </transition>
    <transition name="fade">
      <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
    </transition>
    <div>
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
        this.$router.replace({ path: '/worlddistribute' })
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
          this.drawpiegraph('Echa')
        })
      },
      async drawpiegraph(id) {
        var self = this
        this.chart = echarts.init(document.getElementById(id))
        this.chart.setOption({
          backgroundColor: '#333366',
          title: {
            text: 'Github 占比最大前十语言',
            left: 'center',
            top: 40,
            textStyle: {
              color: '#ccc'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          visualMap: {
            show: false,
            min: 600,
            max: 600,
            inRange: {
              colorLightness: [0, 1]
            }
          },
          series: [
            {
              name: '语言及仓库数量：',
              type: 'pie',
              radius: '75%',
              center: ['50%', '50%'],
              data: this.piedata.sort(function (a, b) { return a.value - b.value }),
              roseType: 'angle',
              label: {
                normal: {
                  textStyle: {
                    color: 'rgba(255, 255, 255, 0.3)'
                  }
                }
              },
              labelLine: {
                normal: {
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.5)'
                  },
                  smooth: 0.2,
                  length: 10,
                  length2: 20
                }
              },
              itemStyle: {
                normal: {
                  color: '#c23531',
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
        })
        this.chart.on('click', function (param) {
          console.log(param.name)
          self.$router.push({ path: '/language', query: { name: param.name } })
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
          this.drawbargraph('barEcha')
        })
      },
      async drawbargraph(id) {
        var self = this
        this.chart = echarts.init(document.getElementById(id))
        this.chart.setOption({
          backgroundColor: '#333366',
          title: {
            text: '仓库及用户数量',
            textStyle: {
              color: '#ccc'
            }
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            textStyle: {
              color: '#ccc'
            },
            data: ['仓库数量', '用户数量']
          },
          toolbox: {
            show: true,
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
                  color: '#ccc'
                }
              },
              axisLabel: {
                interval: 0,
                rotate: -30,
                textStyle: {
                  color: '#ccc'
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
                  color: '#ccc'
                }
              },
              axisLabel: {
                show: true,
                textStyle: {
                  color: '#ccc'
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
              }
            },
            {
              name: '用户数量',
              type: 'bar',
              itemStyle: {
                normal: {
                  color: '#00fe00'
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
        })
        this.chart.on('click', function (param) {
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
  height: 100%;
  margin:0px;
  min-width: 1366px;
}
.chart-container{
  height: 600%;
  width: 50%
}
.Echa{
  height:600px;
  width:50%;
  margin-top: 100px;
  float: left;
}
.barEcha{
  height:600px;
  width:50%;
  margin-top: 100px;
  float: left;
}
.selector-container{
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  margin: 50px;
}
.selector-box{
  display: inline-block;
  line-height: 60px;
  width: 150px;
  height: 60px;
  font-size: 1.4rem;
  color: #fff;
  text-align: center;
  cursor: pointer;
  background-color: #333333;
}
</style>