import Vue from 'vue'
import Router from 'vue-router'
import First from '@/components/First'
import LanguageYear from '@/components/LanguageYear'
import SearchResult from '@/components/SearchResult'
import '../../node_modules/china.js'
import '../assets/styles/common.css'
import '../assets/styles/initial.css'
import '../assets/styles/navigation.css'
import Nav from '@/components/Nav'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Nav,
      children: [
        {
          path: '/',
          component: First,
          name: 'nav'
        },
        {
          path: '/language',
          name: 'languageyear',
          component: LanguageYear
        },
        {
          path: '/search',
          name: 'search',
          component: SearchResult
        }
      ]
    }
  ]
})
