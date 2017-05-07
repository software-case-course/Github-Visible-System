<template>
<div class="searchresult_content">
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
  <div class="List">
    <div class = "items">
      <ul v-for="item in searchdata">
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
          <li>
            Score:&nbsp &nbsp{{item.score}}
          </li>
          <li>
            <a>The Owner:&nbsp</a>
        <a v-html="item.ownername" v-bind:href="item.ownerurl" target="_blank"></a>
      </li>
    </ul>
    </div>
  </div>
</div>
</template>

<script>
import _ from 'lodash'
export default {
  name: 'search',
  mounted () {
    this.$nextTick(function () {
      this.keyword = _.cloneDeep(this.$route.query.keyword)
      console.log(this.keyword)
      this.getsearchresult()
    })
  },
  data () {
    return {
      searchdata: [],
      keyword: null,
      show: false
    }
  },
  methods: {
    async getsearchresult () {
      var data1 = []
      await this.$http.get('https://api.github.com/search/repositories?q=' + this.keyword + '&sort=forks').then(response => {
        console.log(response)
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
        console.log(data1)
        this.searchdata = _.cloneDeep(data1)
        // console.log(this.searchdata)
      })
    },
    search_onclick () {
      // console.log(this.search_input)
      this.$router.push({path: '/search', query: {keyword: this.search_input}})
    }
  }
}
</script>

<style scoped>
.searchresult_content{
  width: 100%;
  background-color: #333366;
  min-width: 800px;
  padding-top: 100px;
  padding-bottom: 20px;
}
.List{
  width: 90%;
  margin: 0 auto;
}
.items{
  display: flex;
  flex-wrap: wrap;
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
.List ul:hover{
  background-color: #dd6d6d;
}
.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #CCC;
  text-decoration: none;
}
.List li,.List li>a{
  font-size: 1.2em;
  overflow: hidden;
}
.List li{
  margin: 5px 0;
  user-select: text;
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
  position: fixed;
  top: 0;
  z-index: 2;
}
.information-box{
  width: 100%;
  height: 62%;
  background-color: #CC0033;
  position: fixed;
  top: 0;
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
</style>
