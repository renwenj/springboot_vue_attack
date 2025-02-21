
import Vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "@/router";
Vue.use(Vuex)

function addNewRoute(menuList) {
    console.log(menuList)
    let routes=router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if(routeItem.path=='/MyIndex'){
            menuList.forEach(menu=>{
                let childRoute={
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:'管理员管理'
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }
                routeItem.children.push(childRoute)
            })
        }
    })
    resetRouter()
    router.addRoutes(routes)
    /*{
        path:'/MyAdmin',
            name:'myadmin',
        meta:{
        title:'管理员管理'
    },
        component:()=>import('../components/myadmin/MyAdminManage.vue')
    }*/
}

export default new Vuex.Store({
    state:{
        menu:[]
    },
    mutations:{
        setMenu(state,menuList){
            state.menu=menuList
            addNewRoute(menuList)
        }
    },
    getters:{
        getMenu(state){
            return state.menu
        }
    }
})