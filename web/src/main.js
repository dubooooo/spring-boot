import 'iview/dist/styles/iview.css'
import Vue from 'vue'
import App from './App'
import iView from 'iview'
import router from './config/router'
import Router from "vue-router";

Vue.use(iView)
Vue.use(Router)

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
