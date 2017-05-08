<template>
<div class="languageyear_content">
    <!--<nav>
      <a href="#" class="back-icon"><img src="../assets/images/ic_arrow_back_white_36dp_1x.png"/></a>
    </nav>-->
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
    <chart id="linegraph" class="linegraph"></chart>
    <div class="List">
      <ul v-for="item in languagetop30data">
        <a v-html="item.name" v-bind:href="item.url" target="_blank"></a>
        <li>
          Description:&nbsp &nbsp{{item.des}}
        </li>
        <li>
          Fork Count:&nbsp &nbsp{{item.fork}}
        </li>
        <li>
          Watchers Count:&nbsp &nbsp{{item.watch}}
        </li>
        <li>
          Star Count:&nbsp &nbsp{{item.star}}
        </li>
        <li>Score:&nbsp &nbsp{{item.score}}</li>
        <li>
          <a>The Owner:&nbsp</a>
          <a v-html="item.ownername" v-bind:href="item.ownerurl" target="_blank"></a>
        </li>
      </ul>
    </div>
</div>
</template>

<script>
import _ from 'lodash'
import echarts from 'echarts'
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'Languageyear',
  computed: {
    ...mapGetters(['shownav'])
  },
  mounted () {
    this.$nextTick(function () {
      var ndate = new Date()
      ndate.toLocaleDateString()
      this.thisyear = ndate.getFullYear()
      // console.log(this.thisyear)
      var name = _.cloneDeep(this.$route.query.name)
      console.log(name)
      this.languageid = name
      this.getbackdata()
      this.languagetop30()
    })
  },
  methods: {
    ...mapActions(['changenav']),
    setshow () {
      this.changenav()
    },
    async getbackdata () {
      var data1 = []
      var data2 = []
      var data3 = []
      await this.$http.get('http://www.kongin.cn/git-view/search/year').then(response => {
        // console.log(response)
        let year1 = this.thisyear - 10 + 1
        for (let i = 0; i < response.body.length;) {
          let res = response.body[i]
          i++
          if (res.language === this.languageid && res.year === year1) {
            i = 0
            year1++
            var repo = (res.repo)
            var pushnum = (res.push)
            var yearnum = (res.year)
            data1.push(repo)
            data2.push(pushnum)
            data3.push(yearnum)
          }
        }
        this.repodata = (data1)
        this.pushdata = (data2)
        this.yeardata = (data3)
        // console.log(this.repodata)
        // console.log(this.pushdata)
        // console.log(this.yeardata)
      })
      this.drawline('linegraph')
    },
    async drawline (id) {
      var colors = ['#5793f3', '#d14a61', '#ccc']
      this.chart = echarts.init(document.getElementById(id))
      this.chart.setOption({
        title: {
          text: this.languageid + '最近10年上传折线图',
          left: 'center',
          top: 20
        },
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        legend: {
          data: ['代码总量', '代码年上传量']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            data: this.yeardata
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '代码总量',
            min: 0,
            max: Math.max.apply(null, this.repodata) + Math.max.apply(null, this.repodata) / 10,
            position: 'right',
            axisLine: {
              lineStyle: {
                color: colors[0]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '年上传量',
            min: 0,
            max: Math.max.apply(null, this.pushdata) + Math.max.apply(null, this.pushdata) / 10,
            position: 'left',
            axisLine: {
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '代码总量',
            type: 'bar',
            data: this.repodata
          },
          {
            name: '代码年上传量',
            type: 'line',
            yAxisIndex: 1,
            data: this.pushdata
          }
        ]
      })
    },
    async languagetop30 () {
      var data1 = []
      await this.$http.get('https://api.github.com/search/repositories?q=language:' + this.languageid + '&sort=forks').then(response => {
        // console.log(response)
        for (let i = 0; i < response.body.items.length; i++) {
          let res = response.body.items[i]
          var name = _.cloneDeep(res.name)
          var des = _.cloneDeep(res.description)
          var fork = _.cloneDeep(res.forks_count)
          var watch = _.cloneDeep(res.watchers_count)
          var star = _.cloneDeep(res.stargazers_count)
          var url = _.cloneDeep(res.html_url)
          var ownername = _.cloneDeep(res.owner.login)
          var ownerurl = _.cloneDeep(res.owner.html_url)
          var score = _.cloneDeep(res.score)
          data1.push({name, des, watch, fork, star, url, ownername, ownerurl, score})
        }
        this.languagetop30data = _.cloneDeep(data1)
      })
    }
  },
  data () {
    return {
      chart: null,
      repodata: [],
      pushdata: [],
      yeardata: [],
      thisyear: null,
      languageid: null,
      languagetop30data: []
    }
  }
}
</script>

<style scoped>
.languageyear_content{
  width: 100%;
  background-color: #333366;
  min-width: 800px;
  padding-bottom: 20px;
}
.linegraph{
  height:600px;
  width:50%;
  margin: 0 auto;
}
.List{
  width: 90%;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  align-items: stretch;
}
.List ul{
  display: inline-block;
  box-sizing: border-box;
  max-width: 600px;
  padding: 20px;
  margin: 10px;
  background-color: #CC0033;
  box-shadow: 5px 5px 20px #000;
}
.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #CCC;
  text-decoration: none;
}
.List li, .List li>a{
  font-size: 1.2em;
}

nav{
  width: 100%;
  height: 60px;
  background-color: #CC0033;
  text-align: center;
}
nav>p{
  display: inline-block;
  height: 60px;
  line-height: 60px;
  float: left;
  color: #fff;
  font-size: 1.6rem;
  padding:0 20px;
}
nav>a{
  display: inline-block;
  width: 100px;
  height: 60px;
  line-height: 60px;
  color: #fff;
  font-size: 1.6rem;
  text-decoration: none;
  float:right;
}
nav>a:hover{
  background-color: #dd6d6d;
}
nav>a:active{
  background-color: #840000;
}
nav>a>img{
  margin: 12px 0;
}
.back-icon{
  float: left;
}
</style>
