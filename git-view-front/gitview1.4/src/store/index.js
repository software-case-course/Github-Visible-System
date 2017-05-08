import Vue from 'vue'
import vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import showNav from './modules/showNav'

Vue.use(vuex)

export default new vuex.Store({
  actions,
  getters,
  modules: {showNav}
})
