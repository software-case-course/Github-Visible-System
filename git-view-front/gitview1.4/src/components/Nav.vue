<template>
<div class="nav_content">
  <nav v-if="!this.shownav">
    <a href="#">首页</a>
    <!--<p>5MAN.com</p>-->
    <!--<a @click="totest">to</a>-->
    <a @click="tologin" id="permsg">{{this.login_permsg}}</a>
    <img v-show="this.downiconshow" @click="tosetpershow" src="../assets/images/ic_arrow_drop_down_white_36dp_1x.png"></img>
    <transition name="selecter-slide">
      <div id="usermsg" v-show="this.showusermsg">
        <!--<p>用户：{{this.login_permsg}}</p>-->
        <label @click="togithub"><p>前往github</p></label>
        <label @click="torepo"><p>前往个人仓库</p></label>
        <label @click="tofollowers"><p>Follower</p></label>
        <label @click="tofollowing"><p>Following</p></label>
        <label @click="tostarred"><p>关注项目</p></label>
        <label @click="topermsg"><p>个人信息</p></label>
        <label><p></p></label>
        <label @click="tosignout"><p>退出</p></label>
      </div>
    </transition>
    <transition name="fade">
      <div v-show="this.showusermsg" class="background-shelter" @click="tosetpershow"></div>
    </transition>
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
      // window.addEventListener('beforeunload', this.handleunload)
      if(((window.location.href).indexOf('code') != -1) && !sessionStorage.getItem('token')){
        this.geturlstr()
      }else if(sessionStorage.getItem('token')) {
        this.getpermsg()
      }
    })
  },
  methods: {
    ...mapActions(['changenav', 'gettokenstr', 'getcodestr', 'setislogined', 'setpermsg', 'sethomeurl', 'setusermsg', 'setlanguagemsg', 'setreposmsg']),
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
    // handleunload() {
    //   alert('555')
    // },
    tologin () {
      if(this.login_permsg === '登录') {
        var url = window.location.href
        if(url.indexOf('#') != -1){
          var urlindex = url.indexOf('#')
          url = url.substr(0, urlindex)
        }
        this.$store.commit('sethomeurl', url)
        // console.log(this.homeurl)
        sessionStorage.setItem('homeurl', this.homeurl)
        this.$store.commit('setislogined', true)
        sessionStorage.setItem('islogin', true)
        window.location.href = ('http://www.kongin.cn/git-view/private/authorize?redirect_uri=' + this.homeurl)
      }else {
        this.$router.push({path: '/personalmsg', query:{name: this.login_permsg}})
      }
    },
    async geturlstr () {
     var nowurl = window.location.href
     var codeindex = nowurl.indexOf('code')
     var urlcode = nowurl.substr(codeindex, 25)
     this.$store.commit('getcodestr', urlcode)
     const response = await this.$http.get('http://www.kongin.cn/git-view/private/gettoken?' + this.codestr)
     // console.log(response)
     if(response.status === 200) {
       var urltoken = response.bodyText
       this.$store.commit('gettokenstr', urltoken)
       sessionStorage.setItem('token',this.tokenstr)
       // console.log(this.tokenstr)
     }
     this.getpermsg()
    },
    async getpermsg () {
      var token = sessionStorage.getItem('token')
      // console.log(token)
      this.$store.commit('gettokenstr', token)
      const response = await this.$http.get('http://www.kongin.cn/git-view/private/user?token=' + this.tokenstr)
      // console.log(response)
      if(response.status === 200) {
        if(response.bodyText.indexOf('error_message') != -1) {
          alert('登陆已失效，请重新登录')
          var url = sessionStorage.getItem('homeurl')
          this.$store.commit('sethomeurl', url)
          sessionStorage.clear()
          this.downiconshow = false
          window.location.href = (this.homeurl)
          this.$store.commit('setislogined', false)
        } else {
          var perjson = []
          perjson = JSON.parse(response.bodyText)
          this.$store.commit('setpermsg', perjson)
          // console.log(this.permsgJson)
          this.login_permsg = this.permsgJson.login
          this.downiconshow = true
          this.$store.commit('setislogined', true)
        }
      }
    },
    tosetpershow () {
      this.showusermsg = !this.showusermsg
    },
    togithub () {
      window.open(this.permsgJson.html_url)
    },
    tofollowers () {
      this.$router.push({path: '/followers', query: {name: this.login_permsg}})
      this.showusermsg = false
    },
    tofollowing () {
      this.$router.push({path: '/following', query:{name: this.login_permsg}})
      this.showusermsg = false
    },
    tostarred () {
      this.$router.push({path: '/starred', query:{name: this.login_permsg}})
      this.showusermsg = false
    },
    tosignout () {
      var url = sessionStorage.getItem ('homeurl')
      sessionStorage.clear()
      console.log(url)
      window.location.href = url
    },
    async torepo () {
      var token = sessionStorage.getItem('token')
      this.$store.commit('gettokenstr', token)
      const response1 = await this.$http.get('http://www.kongin.cn/git-view/private/repos?token=' + this.tokenstr)
      const response2 = await this.$http.get('http://www.kongin.cn/git-view/private/languages?token=' + this.tokenstr)
      // console.log(response1)
      // console.log(response2)
      if((response1.status === 200) && response2.status === 200) {
        if((response1.bodyText.indexOf('error_message') === -1) && (response2.bodyText.indexOf('error_message') === -1)) {
          var data1 = response1.body
          var data2 = response2.body
          // console.log(data2)
          this.$store.commit('setreposmsg', data1)
          this.$store.commit('setlanguagemsg', data2)
          this.showusermsg = false
          this.$router.push({path: '/personalrepo', query: {name: this.login_permsg}})
        } else {
          alert('登录失效，请重新登录')
          this.$store.commit('sethomeurl', sessionStorage.getItem(homeurl))
          sessionStorage.clear()
          window.location.href = this.homeurl
        }
      }
    },
    topermsg () {
      this.$router.push({path: '/personalmsg', query:{name: this.login_permsg}})
      this.showusermsg = false
    }
    // totest () {
    //   this.$router.push({path: '/personalmsg'})
    // }
  },
  computed: {
    ...mapGetters(['shownav', 'codestr', 'tokenstr', 'islogined', 'permsgJson', 'homeurl'])
  },
  data () {
    return {
      downiconshow: false,
      login_permsg: '登录',
      pername: '',
      config: '',
      show: false,
      search_input: '',
      showusermsg: false
    }
  }
}
</script>
<style scoped>
.nav_content{
  width: 100%;
  height: 100%;
  min-width: 510px;
}
#usermsg{
  width: 120px;
  background-color: #222222;
  box-sizing: border-box;
  border: 1px solid #222222;
  z-index: 5;
  position: fixed;
  top:61px;
  left: 100px;
  overflow: hidden;
}
label{
  width: 10%;
  cursor: pointer;
}
label>p{
  font-size: 12px;
  height: 24px;
  border-bottom: 1px solid #206676;
  text-align: center;
  cursor: pointer;
  line-height: 24px;
  color: #ccc;
}
label>p:hover{
  background-color: #38b2ce;
}
p{
  color: #ccc;
  font-size: 16px;
  height: 30px;
  border-bottom: 1px solid #206676;
  text-align: center;
  line-height: 30px;
  cursor: default;
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
nav>img{
  display: inline-block;
  line-height: 60px;
  top:"center";
  margin-top: 18px;
  margin-left: -22px;
  cursor: pointer;
  width:27px; 
  height:27px;
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
  font-size: 1.8rem;
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
    width: 80px;
  }
}

</style>

