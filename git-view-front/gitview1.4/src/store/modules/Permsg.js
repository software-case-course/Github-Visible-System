const state = {
  permsgJson: [],
  usermsgJson: []
}
const getters = {
  permsgJson: state => state.permsgJson,
  usermsgJson: state => state.usermsgJson
}
const mutations = {
  setpermsg (state, permsg) {
    state.permsgJson = permsg
  },
  setusermsg (state, usermsg) {
    state.usermsgJson = usermsg
  }
}
const actions = {
  setpermsg ({commit}) {
    commit('setpermsg')
  },
  setusermsg ({commit}) {
    commit('setusermsg')
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
