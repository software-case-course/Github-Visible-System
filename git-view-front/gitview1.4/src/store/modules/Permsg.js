const state = {
  permsgJson: []
}
const getters = {
  permsgJson: state => state.permsgJson
}
const mutations = {
  setpermsg (state, permsg) {
    state.permsgJson = permsg
  }
}
const actions = {
  setpermsg ({commit}) {
    commit('setpermsg')
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
