<template>
  <div class="starred_cotent">
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
    <label><h2>我的Star:</h2></label>
    <div class="List">
      <ul v-for="item in starredmsg">
        <a v-bind:href="item.html_url" v-html="item.name" target="_blank"></a>
        <hr>
        <li>
            Description:&nbsp &nbsp{{item.description}}
          </li>
          <li>
            Language:&nbsp &nbsp{{item.language}}
          </li>
          <li>
            Fork Count:&nbsp &nbsp{{item.forks_count}}
          </li>
          <li>
            Watchers Count:&nbsp &nbsp{{item.watchers_count}}
          </li>
          <li>
            <a>The Owner:&nbsp</a>
            <a v-html="item.owner.login" v-bind:href="item.owner.html_url" target="_blank"></a>
          </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'starred',
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
      starredurl: '',
      starredmsg: []
    }
  },
  methods: {
    ...mapActions(['changenav']),
    setshow () {
      this.changenav()
    },
    getdata () {
      var url = this.permsgJson.starred_url
      var urlindex = url.indexOf('{')
      this.starredurl = url.substr(0,urlindex)
      //console.log(this.starredurl)
      this.getstarredmsg()
    },
    async getstarredmsg () {
      const response = await this.$http.get(this.starredurl)
      console.log(response)
      this.starredmsg = response.body
      console.log(this.starredmsg)
    }
  }
}
</script>

<style scoped>
.starred_content{
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
  border-top: 8px solid #003399;
  background-color: #fff;
  box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.5);
}

.List ul:hover{
  transition: transform 0.4s;
  transform: scale(1.05);
}

.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #555555;
  text-decoration: none;
}
.List hr{
  height: 2px;
  background-color: #f1f1f1;
  border: 0;
}
.List p {
  display: inline-block;
  color: #555555;
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

