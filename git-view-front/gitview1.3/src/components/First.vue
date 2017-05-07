<template>
<div class="main_content">
    <nav v-if="!show">
      <!--<p>5MAN.com</p>-->
      <a href="#">登录</a>
      <a href="#">仓库</a>
      <!--<p @click="barEchaShow = !barEchaShow">仓库及用户数量</p>
      <p @click="echaShow = !echaShow">Github占比前十语言</p>-->
      <button type="submit" class="search-icon" @click="search_onclick">
        <img src="../assets/images/ic_search_white_36dp_1x.png" />
      </button>
      <input type="search" id="search" class="search-box" placeholder="搜索仓库" v-model="search_input" @keyup.13="search_onclick"/>
      <img v-show="!show" src="../assets/images/ic_arrow_drop_down_white_36dp_1x.png" class="slide-down-icon" @click="show = !show"/>
    </nav>
    <transition name="slide">
      <div v-show="show" class="information-box">
        <!--<form>
        </form>-->
        <!--<div class="user-head-picture-box"></div>-->
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="show = !show"/>
      </div>
    </transition>
    <transition name="fade">
      <div v-show="show" class="background-shelter" @click="show = !show"></div>
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
export default {
  name: 'first',
  mounted () {
    this.$nextTick(function () {
      this.getpiedata()
      this.getbardata()
    })
  },
  data () {
    return {
      chart: null,
      piedata: [],
      bardatalanguage: [],
      bardatarepo: [],
      bardatausers: [],
      id: null,
      show: false,
      echaShow: true,
      barEchaShow: true
    }
  },
  methods: {
    test () {
      console.log()
    },
    async getpiedata () {
      this.piedata = []
      var data1 = []
      await this.$http.get('http://www.kongin.cn/git-view/search/languages').then(response => {
        // console.log(response)
        for (let i = 0; i < response.body.length; i++) {
          let res = response.body[i]
          var value = _.cloneDeep(res.repo)
          var name = _.cloneDeep(res.language)
          data1.push({value, name})
          this.piedata = _.cloneDeep(data1)
        }
        // console.log(this.piedata)
        this.drawpiegraph('Echa')
      })
    },
    async drawpiegraph (id) {
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
            name: '访问来源',
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
        self.$router.push({ path: '/language', query: {name: param.name} })
      })
    },
    async getbardata () {
      this.bardatalanguage = []
      this.bardatarepo = []
      this.bardatausers = []
      await this.$http.get('http://www.kongin.cn/git-view/search/languages').then(response => {
        console.log(response)
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
    async drawbargraph (id) {
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
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
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
                {type: 'average', name: '平均值'}
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
        self.$router.push({ path: '/language', query: {name: param.name} })
      })
    },
    search_onclick () {
      // console.log(this.search_input)
      this.$router.push({path: '/search', query: {keyword: this.search_input}})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main_content{
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
nav{
  width: 100%;
  min-width: 800px;
  height: 60px;
  background-color: #CC0033;
  position: fixed;
  top: 0;
  z-index: 4;
}
nav>p{
  display: inline-block;
  height: 60px;
  line-height: 60px;
  float: right;
  color: #fff;
  font-size: 1.6rem;
  padding: 0 20px;
  cursor: pointer;
}
nav>a{
  display: inline-block;
  width: 100px;
  height: 60px;
  line-height: 60px;
  color: #fff;
  font-size: 1.6rem;
  text-decoration: none;
  text-align: center;
  float:left;
}
nav>a:hover{
  background-color: #dd6d6d;
}
nav>a:active{
  background-color: #840000;
}
nav>p:hover{
  background-color: #dd6d6d;
}
nav>p:active{
  background-color: #840000;
}
/*nav>img{
  margin-top: 24px;
  cursor: pointer;
}*/
.background-shelter{
  width: 100%;
  height: 100%;
  background-color: #000;
  opacity: 0.6;
  position: absolute;
  z-index: 2;
}
.information-box{
  width: 100%;
  height: 62%;
  background-color: #CC0033;
  position: absolute;
  z-index: 3;
}
.slide-enter-active{
  animation-name: slide-down;
  animation-duration: 0.5s;
}
.slide-leave-active{
  animation-name: slide-up;
  animation-duration: 0.5s;
}
@keyframes slide-down{
  0%{
    height: 60px;
  }
  100%{
    height: 62%;
  }
}
@keyframes slide-up{
  0%{
    height: 62%;
  }
  100%{
    height: 0;
  }
}
.fade-enter-active{
  animation-name: fade-in;
  animation-duration: 0.5s;
}
.fade-leave-active{
  animation-name: fade-out;
  animation-duration: 0.5s;
}
@keyframes fade-in{
  0%{
    opacity: 0;
  }
  100%{
    opacity: 0.6;
  }
}@keyframes fade-out{
  0%{
    opacity: 0.6;
  }
  100%{
    opacity: 0;
  }
}
.slide-up-icon{
  position: absolute;
  bottom: 0;
  right: 50%;
  cursor: pointer;
}
.slide-down-icon{
  position: absolute;
  bottom: 0;
  right: 50%;
  cursor: pointer;
}
.search-box{
  width: 200px;
  height: 30px;
  margin: 15px 0;
  border-radius: 3px;
  border: none;
  padding: 5px;
  outline: none;
  font-size: 16px;
  float: right;
}
.search-icon{
  height: 40px;
  margin: 10px 0;
  background: transparent;
  float: right;
  outline: none;
  border: none;
  cursor: pointer;
}
/*.user-head-picture-box{
  width: 200px;
  height: 200px;
  background-color: #000;
  position: absolute;
  bottom: 30%;
  left: 15%;
  border: 5px solid #fff;
}*/
</style>
