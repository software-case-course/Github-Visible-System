<template>
<div class="nav_content">
  <nav v-if="!this.shownav">
    <a href="#">首页</a>
    <!--<p>5MAN.com</p>-->
    <a @click="tologin" id="permsg">{{this.login_permsg}}</a>
    <img v-show="this.downiconshow" src="../assets/images/ic_arrow_drop_down_white_36dp_1x.png"></img>
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
      window.addEventListener('beforeunload', this.handleunload)
      if(((window.location.href).indexOf('code') != -1) && !this.$session.has('token')){
        this.geturlstr()
      }else if(this.$session.has('token')) {
        this.getpermsg()
      }
    })
  },
  methods: {
    ...mapActions(['changenav', 'gettokenstr', 'getcodestr', 'setislogined', 'setpermsg', 'sethomeurl']),
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
    handleunload() {
      alert('555')
    },
    tologin () {
      if(this.login_permsg === '登录') {
        var url = window.location.href
        this.$store.commit('sethomeurl', url)
        console.log(this.homeurl)
        this.$session.set('homeurl', this.homeurl)
        this.$store.commit('setislogined', true)
        this.$session.set('islogin', true)
        window.location.href = ('http://www.kongin.cn/git-view/private/authorize?redirect_uri=' + this.homeurl)
      }else {
        alert('已经登录')
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
       this.$session.set('token',this.tokenstr)
       // console.log(this.tokenstr)
     }
     this.getpermsg()
    },
    async getpermsg () {
      var token = this.$session.get('token')
      console.log(token)
      this.$store.commit('gettokenstr', token)
      const response = await this.$http.get('http://www.kongin.cn/git-view/private/user?token=' + this.tokenstr)
      console.log(response)
      if(response.status === 200) {
        if(response.bodyText.indexOf('error_message') != -1) {
          alert('登陆已失效，请重新登录')
          var url = this.$session.get('homeurl')
          this.$store.commit('sethomeurl', url)
          this.$session.clear()
          window.location.href = (this.homeurl)
        } else {
          this.$session.set('token', '55555')
        }
      }
    }
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
    width: 60px;
  }
}
</style>

