<template>
  <div class="searchresult_content">
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
      <div class="items">
        <ul v-for="item in searchdatare">
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
            <a v-html="item.owner.login" v-bind:href="item.owner.html_url" target="_blank"></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import config from '../config'
  import {
        mapGetters,
        mapActions
    } from 'vuex'
  export default {
    name: 'search',
    created() {
      this.getdata()
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'getdata'
    },
    computed: {
            ...mapGetters(['shownav'])
    },
    data() {
      return {
        searchdatare: [],
        keyword: null,
        show: false
      }
    },
    methods: {
      ...mapActions(['changenav']),
            setshow() {
                this.changenav()
            },
      getdata() {
        this.searchdatare = config.searchdata
      },
      // async getsearchresult () {
      // var data1 = []
      // await this.$http.get('https://api.github.com/search/repositories?q=' + this.keyword + '&sort=forks').then(response => {
      // this.searchdata = response.body.items
      // console.log(this.$parent.$data)
      // console.log(this.searchdatare)
      // for (let i = 0; i < response.body.items.length; i++) {
      //   let res = response.body.items[i]
      //   var name = _.cloneDeep(res.name)
      //   var des = _.cloneDeep(res.description)
      //   var fork = _.cloneDeep(res.forks_count)
      //   var watch = _.cloneDeep(res.watchers_count)
      //   var star = _.cloneDeep(res.stargazers_count)
      //   var url = _.cloneDeep(res.html_url)
      //   var ownername = _.cloneDeep(res.owner.login)
      //   var ownerurl = _.cloneDeep(res.owner.html_url)
      //   var score = _.cloneDeep(res.score)
      //   data1.push({name, des, watch, fork, star, url, ownername, ownerurl, score})
      // }
      // console.log(data1)
      // this.searchdata = _.cloneDeep(data1)
      // console.log(this.searchdata)
      // })
      // },
      search_onclick() {
        // console.log(this.search_input)
        this.$router.push({ path: '/search', query: { keyword: this.search_input } })
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