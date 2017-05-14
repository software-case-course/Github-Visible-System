import Vue from 'vue'
import Router from 'vue-router'
import First from '@/components/First'
import LanguageYear from '@/components/LanguageYear'
import SearchResult from '@/components/SearchResult'
import worlddis from '@/components/WorldDistribute'
import Nav from '@/components/Nav'

import '../assets/styles/common.css'
import '../assets/styles/initial.css'
import '../assets/styles/navigation.css'
import '../../map/china.js'
import '../../map/usa.js'
import '../../map/world.js'
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
        },
        {
          path: '/worlddistribute',
          name: '/worlddis',
          component: worlddis
        }
      ]
    }
  ]
})
