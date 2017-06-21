<template>
  <div class="personalmsg_content">
    <transition name="slide">
      <div v-show="this.shownav" class="information-box">
        <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
      </div>
    </transition>
    <transition name="fade">
      <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
    </transition>
    <div id="permsgshow">
      <form>
        <label><h1 @click="togithub">github账户: {{this.userlogin}}</h1></label>
        <label><p>github id: {{this.id}}</p></label>
        <label><p>用户名： {{this.username}}</p></label>
        <label><p>用户博客： {{this.blog}}</p></label>
        <label><p>用户公司： {{this.company}}</p></label>
        <label><p>用户所在位置： {{this.location}}</p></label>
        <label><p>用户创建时间： {{this.createdtime}}</p></label>
        <label><p>用户最近更新时间： {{this.updatetime}}</p></label>
        <label><p>用户公开仓库数量： {{this.publicrepos}}</p></label>
        <label><p>用户私人仓库数量： {{this.privaterepos}}</p></label>
        <label><p>用户正在关注的仓库数量： {{this.followingcount}}</p></label>
        <label><p>正在关注{{this.userlogin}}的用户数量： {{this.followerscount}}</p></label>
      </form>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'personalmsg',
  computed: {
    ...mapGetters(['shownav', 'permsgJson'])
  },
  mounted() {
    this.$nextTick (function () {
      this.getdata()
    })
  },
  methods: {
    ...mapActions(['changenav']),
    setshow() {
      this.changenav()
    },
    getdata () {
      this.permsg = this.permsgJson
      // console.log(this.permsg)
      if(!this.permsg) {
        alert('登录已失效，请重新登录')
      }
      else{
        this.blog = this.filter(this.permsg.blog)
        this.company = this.filter(this.permsg.company)
        this.createdtime = this.filter(this.permsg.created_at)
        this.useremail = this.filter(this.permsg.email)
        this.followerscount = this.filter(this.permsg.followers)
        this.followingcount = this.filter(this.permsg.following)
        this.htmlurl = this.filter(this.permsg.html_url)
        this.location = this.filter(this.permsg.location)
        this.userlogin = this.filter(this.permsg.login)
        this.username = this.filter(this.permsg.name)
        this.privaterepos = this.filter(this.permsg.owned_private_repos)
        this.publicrepos = this.filter(this.permsg.public_repos)
        this.updatetime = this.filter(this.permsg.updated_at)
        this.id = this.filter(this.permsg.id)
        var timeindex = this.updatetime.indexOf('T')
        this.updatetime = this.updatetime.substr(0, timeindex)
        this.createdtime = this.createdtime.substr(0, timeindex)
      }
    },
    togithub () {
      window.open(this.htmlurl)
    },
    filter(getdata) {
      var backdata = ''
      switch (getdata) {
        case '': backdata = '无'; break
        case null: backdata = '无'; break
        default: backdata = getdata
      }
      return backdata
    }
  },
  data:function() {
    return {
      permsg: [],
      id: '',
      blog: '',
      company: '',
      createdtime: '',
      useremail: '',
      followerscount: '',
      followingcount: '',
      htmlurl: '',
      location: '',
      username: '',
      userlogin: '',
      privaterepos: '',
      publicrepos: '',
      updatetime: '',
    }
  }
}
</script>

<style scoped>
  .personalmsg_content{
    padding-top: 60px;
    padding-bottom: 60px;
  }
  #permsgshow{
    margin-left:15%;
    margin-right:15%;
    background-color:transparent;
    width: 70%;
    height: 100%;
    left: center;
    text-align: center;
  }
  label{
    width: 100%;
    cursor: pointer;
  }
  label>h1{
    margin-top: 30px;
    padding-bottom: 20px;
    width:100%;
    font-size: 4rem;
    border-bottom: 1px solid #206676;
    text-align: center;
    line-height: 60px;
    height: 60px;
    cursor: pointer;
    color: #155fea;
  }
  label>h1:hover{
    color: #ff0d00;
  }
  label p{
    width:100%;
    font-size: 1.6rem;
    height: 42px;
    line-height: 42px;
    text-align: center;
    cursor: pointer;
  }
</style>

