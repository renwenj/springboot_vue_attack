import VueRouter from "vue-router";
const routes=[
    {
        path:'/',
        name:'login',
        component:()=>import('../components/MyLogin.vue')
    },
    {
        path:'/MyIndex',
        name:'MyIndex',
        component:()=>import('../components/MyIndex.vue'),
        children:[
            {
                path:'/MyHome',
                name:'myhome',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/MyHome.vue')
            },
            /*{
                path:'/MyAdmin',
                name:'myadmin',
                meta:{
                    title:'管理员管理'
                },
                component:()=>import('../components/myadmin/MyAdminManage.vue')
            },
            {
                path:'/MyUser',
                name:'myuser',
                meta:{
                    title:'用户管理'
                },
                component:()=>import('../components/myuser/MyUserManage.vue')
            }*/
        ]
    }
]

const router=new VueRouter(
    {
        mode:'history',
        routes
    }
)
export function resetRouter(){
    router.matcher=new VueRouter({
        mode:'history',
        routes:[]
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push;

VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err);
};
export default router;