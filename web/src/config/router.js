import Router from 'vue-router'
import Memu from '@/components/memu/memu'

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Memu',
      component: Memu
    }
  ]
})
