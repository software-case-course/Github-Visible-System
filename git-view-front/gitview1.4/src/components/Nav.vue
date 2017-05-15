<template>
<div class="nav_content">
  <nav v-if="!this.shownav">
    <a href="#">首页</a>
    <!--<p>5MAN.com</p>-->
    <a>登录</a>
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
  methods: {
    ...mapActions(['changenav']),
    async search_onclick () {
      await this.$http.get('https://api.github.com/search/repositories?q=' + this.search_input + '&sort=forks').then(response => {
        config.searchdata = response.body.items
      })
      this.$router.replace({path: '/search', query: {keyword: this.search_input}})
    },
    setShow () {
      // console.log(this.shownav)
      this.changenav()
      // console.log(this.shownav)
      // config.showNav = !config.showNav
    },
    
  },
  computed: {
    ...mapGetters(['shownav'])
    // configShow: function () {
    //   // console.log(config.showNav)
    //   // let result = this.setShownav()
    //   // console.log(result)
    //   config.showNav = this.show
    //   return config.showNav
    // },
    // num: function () {
    //   this.num2 = this.num1 + 3
    // //   return this.num2
    // }
  },
  data () {
    return {
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
  overflow: auto;
}
nav{
  width: 100%;
  min-width: 800px;
  height: 60px;
  background-color: #CC0033;
  position: fixed;
  top: 0;
  z-index: 4;
  box-shadow: 0 0 20px #000;
}
nav>p{
  display: inline-block;
  height: 60px;
  line-height: 60px;
  float: right;
  color: #fff;
  font-size: 1.6rem;
  padding: 0 20px;
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
  cursor: pointer;
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

