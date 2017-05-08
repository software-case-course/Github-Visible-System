
const state = {
  shownav: false
}

const getters = {
  shownav: state => state.shownav
}

const mutations = {
  changenav (state) {
    state.shownav = !state.shownav
    console.log(state.shownav)
  }
}

const actions = {
  changenav ({commit}) {
    commit('changenav')
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
