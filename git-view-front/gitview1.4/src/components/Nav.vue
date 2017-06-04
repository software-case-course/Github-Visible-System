<template>
<div class="nav_content">
  <nav v-if="!this.shownav">
    <a href="#">首页</a>
    <!--<p>5MAN.com</p>-->
    <a @click="tologin" id="permsg">登录</a>
    <!--<p @click="barEchaShow = !barEchaShow">仓库及用户数量</p>
    <p @click="echaShow = !echaShow">Github占比前十语言</p>-->
    <button type="submit" class="search-icon" @click="search_onclick">
      <img src="../assets/images/ic_search_white_36dp_1x.png" />
    </button>
    <input type="search" id="search" class="search-box" placeholder="搜索仓库" v-model="search_input" @keyup.13="search_onclick"/>
    <img v-show="!this.shownav" src="../assets/images/ic_arrow_drop_down_white_36dp_1x.png" class="slide-down-icon" @click="setShow"/>
  </nav>
  <router-view></router-view>
</div>
</template>
<script>
import config from '../config'
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'nav',
  mounted() {
    this.$nextTick(function () {
      if(window.location.href.indexOf('code') != -1){
        this.geturlstr()
      }
    })
  },
  methods: {
    ...mapActions(['changenav', 'gettokenstr', 'getcodestr', 'setislogined']),
    async search_onclick () {
      await this.$http.get('https://api.github.com/search/repositories?q=' + this.search_input + '&sort=forks').then(response => {
        config.searchdata = response.body.items
        // console.log(response)
      })
      this.$router.replace({path: '/search', query: {keyword: this.search_input}})
    },
    setShow () {
      this.changenav()
    },
    tologin () {
      window.open('http://www.kongin.cn/git-view/private/authorize?redirect_uri=' + window.location.href)
      // var urlstr = window.location
      // console.log(urlstr)
    },
    async geturlstr () {
      var islogin = this.$localStorage.get('islogin')
      this.$store.commit('setislogined', islogin)
      var urlstr = window.location.href
      // console.log(urlstr)
      if(!this.islogined) {
        var indexofcode = urlstr.indexOf('code')
        var code = urlstr.substr(indexofcode, 25)
        this.$store.commit('getcodestr',code)
        console.log(this.codestr)
        const response1 = await this.$http.get('http://www.kongin.cn/git-view/private/gettoken?' + this.codestr)
        if(response1.status === 200){
          var token = response1.bodyText
          this.$store.commit('gettokenstr', token)
          this.$localStorage.set('token', token)
          console.log('登录成功')
          this.$store.commit('setislogined', true)
          this.$localStorage.set('islogin', this.islogined)
        }
      }
      // console.log(code)
      else {
        var token = this.$localStorage.get('token')
        this.$store.commit('gettokenstr', token)
        console.log('已经登录')
      }
      console.log(this.tokenstr)
      // const response2 = await this.$http.get('http://www.kongin.cn/git-view/private/user?token=' + this.tokenstr)
      // console.log(response2)
    }
  },
  computed: {
    ...mapGetters(['shownav', 'codestr', 'tokenstr', 'islogined'])
  },
  data () {
    return {
      pername: '',
      config: '',
      show: false,
      search_input: ''
    }
  }
}
</script>
<style scoped>
.nav_content{
  width: 100%;
  height: 100%;
  min-width: 400px;
}
nav{
  position: fixed;
  top: 0;
  z-index: 4;
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  background-color: #222222;
  border-bottom: 1px solid #ccc;
}
nav>p{
  display: inline-block;
  height: 60px;
  line-height: 60px;
  float: right;
  color: #155fea;
  font-size: 1.6rem;
  padding: 0 20px;
}
nav>a{
  display: inline-block;
  width: 100px;
  height: 60px;
  line-height: 60px;
  color: #155fea;
  font-size: 1.6rem;
  text-decoration: none;
  text-align: center;
  float:left;
  cursor: pointer;
}
/*nav>a:hover{
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
}*/
/*nav>img{
  margin-top: 24px;
  cursor: pointer;
}*/
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
@media screen and (max-width: 500px){
  .search-box{
    width: 100px;
  }
  nav>a{
    width: 60px;
  }
}
</style>

