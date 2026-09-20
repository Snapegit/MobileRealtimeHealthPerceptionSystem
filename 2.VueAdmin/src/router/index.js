	import {
		createRouter,
		createWebHashHistory
	} from 'vue-router'
	import news from '@/views/news/list'
	import tongzhijingbao from '@/views/tongzhijingbao/list'
	import gexinghuajianyi from '@/views/gexinghuajianyi/list'
	import yonghu from '@/views/yonghu/list'
	import jiankangfenxi from '@/views/jiankangfenxi/list'
	import config from '@/views/config/list'
	import jiankangxinxi from '@/views/jiankangxinxi/list'

export const routes = [{
		path: '/login',
		name: 'login',
		component: () => import('../views/login.vue')
	},{
		path: '/',
		name: '首页',
		component: () => import('../views/index'),
		children: [{
			path: '/',
			name: '首页Home',
			component: () => import('../views/HomeView.vue'),
			meta: {
				affix: true
			}
		}, {
			path: '/updatepassword',
			name: '修改密码',
			component: () => import('../views/updatepassword.vue')
		}
		
		,{
			path: '/news',
			name: '健康资讯',
			component: news
		}
		,{
			path: '/tongzhijingbao',
			name: '通知警报',
			component: tongzhijingbao
		}
		,{
			path: '/gexinghuajianyi',
			name: '个性化建议',
			component: gexinghuajianyi
		}
		,{
			path: '/yonghu',
			name: '用户',
			component: yonghu
		}
		,{
			path: '/jiankangfenxi',
			name: '健康分析',
			component: jiankangfenxi
		}
		,{
			path: '/config',
			name: '轮播图',
			component: config
		}
		,{
			path: '/jiankangxinxi',
			name: '健康信息',
			component: jiankangxinxi
		}
		]
	},
]

const router = createRouter({
	history: createWebHashHistory(process.env.BASE_URL),
	routes
})

export default router
