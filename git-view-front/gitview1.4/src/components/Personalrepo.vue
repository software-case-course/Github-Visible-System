<template>
  <div class="personalrepo_contnent">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <!--<form>
      </form>-->
        <!--<div class="user-head-picture-box"></div>-->
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
      </div>
    </transition>
    <transition name="fade">
      <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
    </transition>
    <div class="List">
        <ul v-for="item in repos">
          <a v-html="item.name" v-bind:href="item.html_url" target="_blank"></a>
          <li>
            Description:&nbsp &nbsp{{item.description}}
          </li>
          <li>
            Fork Count:&nbsp &nbsp{{item.forks_count}}
          </li>
          <li>
            Watchers Count:&nbsp &nbsp{{item.watchers_count}}
          </li>
          <li>
            Star Count:&nbsp &nbsp{{item.stargazers_count}}
          </li>
          <li>
            <a>The Owner:&nbsp</a>
            <a v-html="item.owner.login" v-bind:href="item.owner.html_url" target="_blank"></a>
          </li>
        </ul>
    </div>
    <div id="languagegraph"></div>
  </div>
</template>

<script>
import { mapActions, mapGetters} from 'vuex'
import echarts from 'echarts'
import _ from 'lodash'
export default {
  name: 'personalrepo',
  computed: {
    ...mapGetters(['shownav', 'languagemsg', 'reposmsgJson'])
  },
  mounted: function () {
    this.$nextTick(function () {
      this.getdata()
    })
  },
  data () {
    return{
      repos: [],
      language: [],
      languagename: [],
      languagenum: [],
      chart: null,
    }
  },
  methods: {
    ...mapActions(['changenav']),
    setshow () {
      this.changenav()
    },
    getdata () {
      this.repos = this.reposmsgJson
      // console.log(this.repos)
      this.language = this.languagemsg
      console.log(this.language)
      var data1 = []
      var data2 = []
      for(let i = 0; i < this.language.length; i++) {
        var value = this.language[i].code_volume
        var name = this.language[i].language
        data1.push(name)
        data2.push(value)
      }
      this.languagename = _.cloneDeep(data1)
      this.languagenum = _.cloneDeep(data2)
      this.drawgraph('languagegraph')
      console.log(this.languagename)
      // console.log(this.languagenum)
    },
    drawgraph (id) {
      this.chart = echarts.init(document.getElementById(id))
      var option = {
        title: {
          x: 'center',
          text: '个人仓库各语言数量',
        },
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          show: true,
          feature: {
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        grid: {
          borderWidth: 0,
          y: 80,
          y2: 60
        },
        xAxis: [
          {
            type: 'category',
            show: false,
            data: this.languagename
          }
        ],
        yAxis: [
          {
            type: 'value',
            show: false
          }
        ],
        series: [
          {
            name: '个人仓库各语言数量',
            type: 'bar',
            itemStyle: {
              normal: {
                color: function(params) {
                  // build a color map as your need.
                  var colorList = [
                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                  ]
                  return colorList[params.dataIndex]
                },
                label: {
                  show: true,
                  position: 'top',
                  formatter: '{b}\n{c}'
                }
              }
            },
            data: this.languagenum,
            markPoint: {
              tooltip: {
                trigger: 'item',
                backgroundColor: 'rgba(0,0,0,0)',
              }
            }
          }
        ]
      }
      this.chart.setOption(option)
    }
  }
}
</script>

<style scoped>
.personalrepo_content{
  padding-top: 60px;
}
#languagegraph{
  width: 500px;
  height: 350px;
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

.List ul:hover{
  background-color: #003366;
}

.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #fff;
  text-decoration: none;
  cursor: pointer;
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
  .List{
    width: 500px;
  }
  .List ul{
    width: 500px;
  }
}
@media screen and (max-width:500px){
  .List{
    width: 400px;
  }
  .List ul{
    width: 350px;
  }
}
</style>

