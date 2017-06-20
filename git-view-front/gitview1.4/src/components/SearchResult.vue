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
        <ul v-for="item in searchdatare">
          <a v-html="item.name" v-on:click="showwarehousedata(item.count);"></a>
          <hr>
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
            Score:&nbsp &nbsp{{item.score}}
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
      async getdata () {
        this.searchdatare = await config.searchdata
        let count = 0;
        // console.log(this.searchdatare)
        this.searchdatare.forEach(item => {
          item.count = count
          count++
        }) 
        // console.log(this.searchdatare)
      },
      showwarehousedata (index){
        this.$router.push({ path: '/person', query: { keyword: this.searchdatare[index].full_name } })
      },
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
  background-color: #ccc;
  padding-top: 100px;
  padding-bottom: 40px;
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
  border-top: 8px solid #003399;
  background-color: #fff;
  box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.5);
}
.List ul:hover{
  transition:transform 0.4s;
  transform: scale(1.05);
}
.List a {
  font-size: 40px;
  font-family: sans-serif;
  color: #555555;
  text-decoration: none;
  cursor: pointer;
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
  .linegraph{
    width: 600px;
    height: 500px;
  }
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
  .linegraph{
    width: 600px;
    height: 400px;
  }
  .List{
    width: 500px;
  }
  .List ul{
    width: 500px;
  }
}
@media screen and (max-width:500px){
  .linegraph{
    width: 400px;
    height: 400px;
  }
  .List{
    width: 400px;
  }
  .List ul{
    width: 350px;
  }
}
</style>