<template>
  <div class="languageyear_content">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
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
            <p>Description :</p>&nbsp &nbsp{{item.des}}
          </li>
          <li>
            <p>Fork Count :</p>&nbsp &nbsp{{item.fork}}
          </li>
          <li>
            <p>Watchers Count :</p>&nbsp &nbsp{{item.watch}}
          </li>
          <li>
            <p>Star Count :</p>&nbsp &nbsp{{item.star}}
          </li>
          <li>
            <p>Score :</p>&nbsp &nbsp{{item.score}}
          </li>
          <li>
            <a>
              <p>The Owner :</p>&nbsp</a>
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
    mounted() {
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
      setshow() {
        this.changenav()
      },
      async getbackdata() {
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
      async drawline(id) {
        var lineColor = '#222222'
        var colors = ['#006699', '#003366', lineColor]
        var myChart = echarts.init(document.getElementById(id))
        var myOption = {
          baseOption: {
            title: {
              text: this.languageid + '最近10年上传折线图',
              left: 'center',
              top: 0,
              textStyle: {
                color: lineColor
              }
            },
            color: colors,
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'cross'
              }
            },
            grid: {
              right: '16%',
              left: '16%'
            },
            toolbox: {
              feature: {
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
              }
            },
            legend: {
              bottom: 0,
              orient: 'horizontal',
              data: ['代码总量', '代码年上传量'],
              textStyle: {
                color: lineColor
              }
            },
            xAxis: [
              {
                type: 'category',
                axisTick: {
                  alignWithLabel: true
                },
                data: this.yeardata,
                axisLine: {
                  show: false,
                  lineStyle: {
                    color: lineColor
                  }
                },
                axisLabel: {
                  textStyle: {
                    fontSize: 12
                  }
                }
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
                  formatter: '{value}',
                  textStyle: {
                    fontSize: 12
                  }
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
                  formatter: '{value}',
                  textStyle: {
                    fontSize: 12
                  }
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
          },
          media: [
            {
              query: {
                maxWidth: 600
              },
              option: {
                toolbox: {
                  show: false
                }
              }
            }
          ]
        }
        window.onresize = myChart.resize
        myChart.setOption(myOption)
      },
      async languagetop30() {
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
            data1.push({ name, des, watch, fork, star, url, ownername, ownerurl, score })
          }
          this.languagetop30data = _.cloneDeep(data1)
        })
      }
    },
    data() {
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
  background-color: #ccc;
  padding-top: 100px;
  padding-bottom: 40px;
}
.linegraph{
  width: 700px;
  height: 500px;
  margin: 0 auto;
}
.List{
  width: 1200px;
  margin-top: 40px;
  margin-right: auto;
  margin-left: auto;
  display: flex;
  flex-wrap: wrap;
  align-items: stretch;
  justify-content: center
}
.List ul{
  display: inline-block;
  box-sizing: border-box;
  width: 500px;
  padding: 20px;
  margin: 20px;
  border-radius: 5px;
  border-left: 20px solid #fff;
  background-color: #155fea;
  box-shadow: 5px 5px 20px #000;
}
.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #fff;
  text-decoration: none;
}
.List p {
  display: inline-block;
  color: #fff;
}
.List li, .List li>a{
  font-size: 1.2em;
  padding: 5px 0;
}

@media screen and (max-width:1200px){
  .linegraph{
    width: 600px;
    height: 500px;
  }
  .List{
    width: 1000px;
  }
  .List ul{
    width: 400px;
  }
}
@media screen and (max-width:1000px){
  .List{
    width: 800px;
  }
  .List ul{
    width: 350px;
  }
}
@media screen and (max-width:800px){
  .linegraph{
    width: 600px;
    height: 400px;
  }
  .List{
    width: 500px;
  }
  .List ul{
    width: 500px;
  }
}
@media screen and (max-width:500px){
  .linegraph{
    width: 400px;
    height: 400px;
  }
  .List{
    width: 400px;
  }
  .List ul{
    width: 350px;
  }
}
</style>