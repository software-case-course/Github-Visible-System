<template>
  <div class="followers_content">
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
    <label><h2>我的Followers:</h2></label>
    <div class="List">
      <ul v-for="item in followersmsg">
        <a v-bind:href="item.html_url" v-html="item.login" target="_blank"></a>
        <li>id: {{item.id}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'followers',
  computed: {
    ...mapGetters(['shownav', 'permsgJson'])
  },
  mounted() {
    this.$nextTick(function () {
      this.getdata()
    })
  },
  data () {
    return{
      followerurl: '',
      followersmsg: []
    }
  },
  methods: {
    ...mapActions(['changenav']),
    setshow() {
      this.changenav()
    },
    getdata () {
      this.followerurl = this.permsgJson.followers_url
      // console.log(this.followerurl)
      if(this.followerurl) {
        this.getfollowermsg()
      } else {
        alert('登录失效，请重新登录')
        window.location.href = sessionStorage.getItem('homeurl')
        sessionStorage.clear()
      }
    },
    async getfollowermsg () {
      const response = await this.$http.get(this.followerurl)
      // console.log(response)
      this.followersmsg = response.body
      // console.log(this.followersmsg)
    }
  }
}
</script>

<style scoped>
.followers_content{
  padding-top: 60px;
}
label{
  width:100%;
  cursor: default;
}
label h2{
  text-align: left;
  font-size: 3rem;
  border-bottom: 1px solid #206766;
  width: 80%;
  height: 60px;
  color: #155fea;
  line-height: 80px;
  margin-left: 10%;
  margin-right: 10%;
  padding-bottom: 20px;
}
.List{
  width: 1200px;
  margin-top: 40px;
  margin-right: auto;
  margin-left: auto;
  display: flex;
  flex-wrap: wrap;
  align-items: stretch;
  justify-content: center;
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

