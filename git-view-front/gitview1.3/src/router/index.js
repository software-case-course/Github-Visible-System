import Vue from 'vue'
import Router from 'vue-router'
import First from '@/components/First'
import LanguageYear from '@/components/LanguageYear'
import WareList from '@/components/WareList'
import SearchResult from '@/components/SearchResult'
import '../../node_modules/china.js'
import '../assets/styles/common.css'
import '../assets/styles/initial.css'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'First',
      component: First
    },
    {
      path: '/language',
      name: 'languageyear',
      component: LanguageYear
    },
    {
      path: '/warelist',
      name: 'warelist',
      component: WareList
    },
    {
      path: '/search',
      name: 'search',
      component: SearchResult
    }
  ]
})
