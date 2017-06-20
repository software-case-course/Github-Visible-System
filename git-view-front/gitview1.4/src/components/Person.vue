<template>
  <div class="person_content">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow"/>
      </div>
    </transition>
    <transition name="fade">
    <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
  </transition>
    <template>
      <!--<input class="repoinput" v-model="name">
      <div id='person'>
        <button v-on:click="show();">show</button>
      </div>
      <div id='daycommit'>
        <button v-on:click="showdaycommit();">show commit</button>
      </div>
      <div id='weekcommit'>
        <button v-on:click="showweekcommit();">show week commit</button>
      </div>-->
      <chart id="linegraph" class="charts"></chart>
      <chart id="commitgraph" class="charts"></chart>
      <chart id="weekcommitgraph" class="charts"></chart>
    </template>
  </div>
</template>

<script>
// import _ from 'lodash'
import echarts from 'echarts'
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'Person',
  mounted () {
    this.$nextTick(function () {
      this.getdata()
      this.getcurddata()
      this.getweeklycommitdata()
    })
  },
  computed: {
    ...mapGetters(['shownav'])
  },
  data () {
    return {
      name: 'airbnb/javascript',
      addtion: [],
      deletion: [],
      moddify: [],
      weekdata: [],
      commitdata: [],
      committotaldata: [],
      commitweek: [],
      /* authorcommitauxiliary: [],
      authorname: [],
      authorcommitquantity: [],
      maxcommit: ''*/
    }
  },
  methods: {
    ...mapActions(['changenav']),
    setshow () {
      this.changenav()
    },
    /*show () {
      this.getdata()
    },
    showdaycommit () {
      this.getcurddata()
    },
    showweekcommit () {
      this.getweeklycommitdata()
    },*/
    async getdata () {
      var adddata = []
      var deletedata = []
      var date = []
      var modifydata = []
      let temp
      await this.$http.get('http://www.kongin.cn/git-view/repos/CodeFrequency?fullname=' + this.$route.query.keyword).then(response => {
        for (let i = 0; i < response.body.length; i++) {
          var add = response.body[i].additions
          var del = response.body[i].deletions
          var dat = response.body[i].week
          var mod = response.body[i].additions + response.body[i].deletions
          adddata.push(add)
          deletedata.push(del)
          date.push(dat)
          modifydata.push(mod)
          this.addtion = adddata
          this.deletion = deletedata
          this.modify = modifydata
          this.weekdata = date
        }
      })
      this.drawchart('linegraph')
    },
    async getcurddata () {
      var commitarray = []
      this.maxcommit = 0
      await this.$http.get('http://www.kongin.cn/git-view/repos/PunchCard?fullname=' + this.$route.query.keyword).then(response => {
        for (let i = 0; i < response.body.length; i++) {
          var com = response.body[i].commit
          if (com > this.maxcommit) {
            this.maxcommit = com
          }
          var day = response.body[i].day
          var time = response.body[i].hour
          commitarray.push([day, time, com])
          this.commitdata = commitarray
        }
      })
      this.drawpunchcard('commitgraph')
    },
    async getweeklycommitdata () {
      var committotalarray = []
      var committotalweek = []
      await this.$http.get('http://www.kongin.cn/git-view/repos/CommitActivity?fullname=' + this.$route.query.keyword).then(response => {
        for (let i = 0; i < response.body.length; i++) {
          if (response.body[i].total > 0) {
            for (let j = i; j < response.body.length; j++) {
              var tot = response.body[j].total
              var cweek = response.body[j].week
              committotalarray.push(tot)
              committotalweek.push(cweek)
              this.committotaldata = committotalarray
              this.commitweek = committotalweek
            }
            i = response.body.length
          }
        }
      })
      this.drawweeklycommit('weekcommitgraph')
    },
    /* async getcomtributordata () {
      var author = []
      var authorcommit = []
      var temparry = []
      let auxiliary
      await this.$http.get('http://www.kongin.cn/git-view/repos/Contributor?fullname=' + this.name).then(response => {
        for (let i = 0; i < response.body.length; i++) {
          if (authorcommit.length === 0) {
            authorcommit[0] = 0 + response.body[i].total
          } else {
            authorcommit[0] = authorcommit[0] + response.body[i].total
          }
        }
        auxiliary = authorcommit[0]
        for (let i = 0; i < response.body.length; i++) {
          let temp = 1
          var authortemp = response.body[i].author.login
          author[0] = 'commit quantity'
          for (let j = 0; j < response.body[i].weeks.length; j++) {
            if (temp === 1) {
              authorcommit[i + 1] = response.body[i].weeks[j].c
              temp = 0
            } else {
              authorcommit[i + 1] = authorcommit[i + 1] + response.body[i].weeks[j].c
            }
          }
          author.push(authortemp)
        }
        this.authorname = author
        temparry = authorcommit
        for (let i = 0; i < authorcommit.length; i++) {
          if (i === 0) {
            this.authorcommitauxiliary[i] = 0
          } else {
            this.authorcommitauxiliary[i] = temparry[0] - authorcommit[i]
            temparry[0] = temparry[0] - authorcommit[i]
          }
          console.log(this.authorcommitquantity[0])
        }
        authorcommit[0] = auxiliary
        this.authorcommitquantity = authorcommit
        this.drawcontributor('author')
      })
    },
    async drawcontributor (id) {
      this.chart = echarts.init(document.getElementById(id))
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter: function (params) {
            var tar = params[1]
            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          splitLine: { show: false },
          data: this.authorname
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '辅助',
            type: 'bar',
            stack: '总量',
            itemStyle: {
              normal: {
                barBorderColor: 'rgba(0,0,0,0)',
                color: 'rgba(0,0,0,0)'
              },
              emphasis: {
                barBorderColor: 'rgba(0,0,0,0)',
                color: 'rgba(0,0,0,0)'
              }
            },
            data: this.authorcommitauxiliary
          },
          {
            name: 'commit quantity',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'inside'
              }
            },
            data: this.authorcommitquantity
          }
        ]
      })
    },*/
    async drawweeklycommit (id) {
      this.chart = echarts.init(document.getElementById(id))
      var lineColor = '#155fea'
      var lineChartOption = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['The number of commit']
        },
        title: {
          left: 'left',
          text: 'weekly commit',
          textStyle: {
            color: lineColor
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.commitweek,
          axisLine: {
            show: true,
            lineStyle: {
              color: lineColor
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: true,
            lineStyle: {
              color: lineColor
            }
          },
          splitLine: {
            lineStyle: {
              color: lineColor
            }
          }
        },
        series: [
          {
            name: 'commit',
            type: 'line',
            stack: '总量',
            data: this.committotaldata
          }
        ]
      }
      this.chart.setOption(lineChartOption)
    },
    async drawpunchcard (id) {
      var hours = ['12a', '1a', '2a', '3a', '4a', '5a', '6a',
        '7a', '8a', '9a', '10a', '11a',
        '12p', '1p', '2p', '3p', '4p', '5p',
        '6p', '7p', '8p', '9p', '10p', '11p']
      var days = ['Sunday', 'Monday', 'Thursday', 'Wednesday', 'Tuesday', 'Friday', 'Saturday']
      var data = []
      var maxdata = this.maxcommit
      // console.log('浏览器' + window.screen.height + '/' +window.screen.width)
      data = this.commitdata.map(function (item) {
        return [item[1], item[0], item[2]]
      })
      this.chart = echarts.init(document.getElementById(id))
      var lineColor = '#155fea'
      var pointChartOption = {
        legend: {
          data: ['Punch Card'],
          left: 'center',
          textStyle: {
            color: lineColor
          }
        },
        title: {
          left: 'left',
          text: 'Punch card',
          textStyle: {
            color: lineColor
          }
        },
        tooltip: {
          position: 'top',
          formatter: function (params) {
            return params.value[2] + ' commits in ' + hours[params.value[0]] + ' of ' + days[params.value[1]]
          }
        },
        grid: {
          left: 2,
          bottom: 10,
          right: 10,
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: hours,
          boundaryGap: false,
          splitLine: {
            show: true,
            lineStyle: {
              color: lineColor,
              type: 'dashed'
            }
          },
          axisLine: {
            show: false,
            lineStyle: {
              color: lineColor
            }
          },
          axisLable: {
            show: true
          }
        },
        yAxis: {
          type: 'category',
          data: days, 
          axisLine: {
            show: true,
            lineStyle: {
              color: lineColor
            }
          },
          axisLable: {
            show: true,
            textStyle: {
              color: '#F8F8FF'
            }
          }
        },
        series: [{
          name: 'Punch Card',
          type: 'scatter',
          itemStyle: {
            normal: {
              color: '#003366'
            }
          },
          symbolSize: function (val) {
            // console.log(val[2])
            return (Math.log(Math.pow(val[2], 1.5)) / Math.log(Math.pow(maxdata * window.screen.width / 800, 1 / 6)) + 2) * 4
          },
          data: data,
          animationDelay: function (idx) {
            return idx * 5
          }
        }]
      }
      this.chart.setOption(pointChartOption)
    },
    async drawchart (id) {
      this.chart = echarts.init(document.getElementById(id))
      var lineColor = '#155fea'
      var barChartOption = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        title: {
          left: 'left',
          text: '增删代码量图',
          textStyle: {
            color: lineColor
          }
        },
        toolbox: {
            feature: {
            /* datazoom: {
              yAxisIndex: 'none'
            },*/
            restore: {},
            saveAsImage: {}
          }
        },
        legend: {
          data: ['代码变化量', '减少代码量', '增加代码量'],
          textStyle: {
            color: lineColor
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'value',
            // boundaryGap: [0, '100%'],
            axisLine: {
              show: true,
              lineStyle: {
                color: lineColor
              }
            },
            splitLine: {
              lineStyle: {
                color: lineColor
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: this.weekdata,
            // boundaryGap: false,
            axisLine: {
              show: false,
              lineStyle: {
                color: lineColor
              }
            },
          }
        ],
        dataZoom: [
          {
            show: true,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            type: "slider",
            start: 1,
            end: 7,
            yAxisIndex: [0],
            borderColor: '#ccc',
            left: '0%',
            }
        ],
        series: [
          {
            name: '代码修改量',
            type: 'bar',
            label: {
              normal: {
                show: true,
                position: 'inside'
              }
            },
            data: this.modify
          },
          {
            name: '增加代码量',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true
              }
            },
            data: this.addtion
          },
          {
            name: '减少代码量',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'left'
              }
            },
            data: this.deletion
          }
        ]
      }
      this.chart.setOption(barChartOption)
    }
  }
}
</script>

<style scoped>
.person_content {
  width: 100%;
  background-color: #ccc;
  padding-top: 100px;
  padding-bottom: 60px;
}

.charts{
  height: 600px;
  width: 60%;
  margin: 50px auto;
}
</style>
