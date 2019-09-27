
package com.example.qsl.fragment.chat;

import android.support.v4.util.ArrayMap;

import com.example.qsl.R;
import com.example.qsl.util.LogUtil;

public class EmotionUtils {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_CLASSIC_TYPE=0x0001;//经典表情

	/**
	 * key-表情文字;
	 * value-表情图片资源
	 */
	public static ArrayMap<String, Integer> EMPTY_MAP;
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


	static {
		EMPTY_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP = new ArrayMap<>();

		EMOTION_CLASSIC_MAP.put("[NO]", R.mipmap.emoji_no);
		EMOTION_CLASSIC_MAP.put("[OK]", R.mipmap.emoji_ok);
		EMOTION_CLASSIC_MAP.put("[下雨]", R.mipmap.emoji_xiayu);
		EMOTION_CLASSIC_MAP.put("[么么哒]", R.mipmap.emoji_mmd);
		EMOTION_CLASSIC_MAP.put("[乒乓]", R.mipmap.emoji_pingpang);
		EMOTION_CLASSIC_MAP.put("[便便]", R.mipmap.emoji_bianbian);
		EMOTION_CLASSIC_MAP.put("[信封]", R.mipmap.emoji_xinfeng);
		EMOTION_CLASSIC_MAP.put("[偷笑]", R.mipmap.emoji_touxiao);
		EMOTION_CLASSIC_MAP.put("[傲慢]", R.mipmap.emoji_aoman);
		EMOTION_CLASSIC_MAP.put("[再见]", R.mipmap.emoji_zaijian);
		EMOTION_CLASSIC_MAP.put("[冷汗]", R.mipmap.emoji_lenghan);
		EMOTION_CLASSIC_MAP.put("[凋谢]", R.mipmap.emoji_diaoxie);
		EMOTION_CLASSIC_MAP.put("[刀]", R.mipmap.emoji_dao);
		EMOTION_CLASSIC_MAP.put("[勾引]", R.mipmap.emoji_gouyin);
		EMOTION_CLASSIC_MAP.put("[发呆]", R.mipmap.emoji_fadai);
		EMOTION_CLASSIC_MAP.put("[发抖]", R.mipmap.emoji_fadou);
		EMOTION_CLASSIC_MAP.put("[可怜]", R.mipmap.emoji_kelian);
		EMOTION_CLASSIC_MAP.put("[可爱]", R.mipmap.emoji_keai);
		EMOTION_CLASSIC_MAP.put("[右哼哼]", R.mipmap.emoji_yhh);
		EMOTION_CLASSIC_MAP.put("[右太极]", R.mipmap.emoji_ytj);
		EMOTION_CLASSIC_MAP.put("[右车头]", R.mipmap.emoji_yct);
		EMOTION_CLASSIC_MAP.put("[吐]", R.mipmap.emoji_tu);
		EMOTION_CLASSIC_MAP.put("[吓]", R.mipmap.emoji_xia);
		EMOTION_CLASSIC_MAP.put("[咒骂]", R.mipmap.emoji_zhouma);
		EMOTION_CLASSIC_MAP.put("[咖啡]", R.mipmap.emoji_kafei);
		EMOTION_CLASSIC_MAP.put("[嘘]", R.mipmap.emoji_xu);
		EMOTION_CLASSIC_MAP.put("[啤酒]", R.mipmap.emoji_pijiu);
		EMOTION_CLASSIC_MAP.put("[困]", R.mipmap.emoji_kun);
		EMOTION_CLASSIC_MAP.put("[多云]", R.mipmap.emoji_duoyun);
		EMOTION_CLASSIC_MAP.put("[大兵]", R.mipmap.emoji_dabing);
		EMOTION_CLASSIC_MAP.put("[大哭]", R.mipmap.emoji_daku);
		EMOTION_CLASSIC_MAP.put("[太阳]", R.mipmap.emoji_taiyang);
		EMOTION_CLASSIC_MAP.put("[回头]", R.mipmap.emoji_huitou);
		EMOTION_CLASSIC_MAP.put("[奋斗]", R.mipmap.emoji_fendou);
		EMOTION_CLASSIC_MAP.put("[奶瓶]", R.mipmap.emoji_naiping);
		EMOTION_CLASSIC_MAP.put("[委屈]", R.mipmap.emoji_weiqu);
		EMOTION_CLASSIC_MAP.put("[害羞]", R.mipmap.emoji_haixiu);
		EMOTION_CLASSIC_MAP.put("[尴尬]", R.mipmap.emoji_ganga);
		EMOTION_CLASSIC_MAP.put("[左哼哼]", R.mipmap.emoji_zuohengheng);
		EMOTION_CLASSIC_MAP.put("[左太极]", R.mipmap.emoji_zuotaiji);
		EMOTION_CLASSIC_MAP.put("[左车头]", R.mipmap.emoji_zuochetou);
		EMOTION_CLASSIC_MAP.put("[差劲]", R.mipmap.emoji_chajin);
		EMOTION_CLASSIC_MAP.put("[弱]", R.mipmap.emoji_ruo);
		EMOTION_CLASSIC_MAP.put("[强]", R.mipmap.emoji_qiang);
		EMOTION_CLASSIC_MAP.put("[彩带]", R.mipmap.emoji_caidai);
		EMOTION_CLASSIC_MAP.put("[彩球]", R.mipmap.emoji_caiqiu);
		EMOTION_CLASSIC_MAP.put("[得意]", R.mipmap.emoji_deyi);
		EMOTION_CLASSIC_MAP.put("[微笑]", R.mipmap.emoji_weixiao);
		EMOTION_CLASSIC_MAP.put("[心碎了]", R.mipmap.emoji_xinsuil);
		EMOTION_CLASSIC_MAP.put("[快哭了]", R.mipmap.emoji_kuaikul);
		EMOTION_CLASSIC_MAP.put("[怄火]", R.mipmap.emoji_ouhuo);
		EMOTION_CLASSIC_MAP.put("[怒]", R.mipmap.emoji_nu);
		EMOTION_CLASSIC_MAP.put("[惊恐]", R.mipmap.emoji_jingkong);
		EMOTION_CLASSIC_MAP.put("[惊讶]", R.mipmap.emoji_jingya);
		EMOTION_CLASSIC_MAP.put("[憨笑]", R.mipmap.emoji_hanxiao);
		EMOTION_CLASSIC_MAP.put("[手枪]", R.mipmap.emoji_shouqiang);
		EMOTION_CLASSIC_MAP.put("[打哈欠]", R.mipmap.emoji_dahaqian);
		EMOTION_CLASSIC_MAP.put("[抓狂]", R.mipmap.emoji_zhuakuang);
		EMOTION_CLASSIC_MAP.put("[折磨]", R.mipmap.emoji_zhemo);
		EMOTION_CLASSIC_MAP.put("[抠鼻]", R.mipmap.emoji_koubi);
		EMOTION_CLASSIC_MAP.put("[抱抱]", R.mipmap.emoji_baobao);
		EMOTION_CLASSIC_MAP.put("[抱拳]", R.mipmap.emoji_baoquan);
		EMOTION_CLASSIC_MAP.put("[挥手]", R.mipmap.emoji_huishou);
		EMOTION_CLASSIC_MAP.put("[拳头]", R.mipmap.emoji_quantou);
		EMOTION_CLASSIC_MAP.put("[握手]", R.mipmap.emoji_woshou);
		EMOTION_CLASSIC_MAP.put("[憋嘴]", R.mipmap.emoji_biezui);
		EMOTION_CLASSIC_MAP.put("[擦汗]", R.mipmap.emoji_cahan);
		EMOTION_CLASSIC_MAP.put("[敲打]", R.mipmap.emoji_qiaoda);
		EMOTION_CLASSIC_MAP.put("[晕]", R.mipmap.emoji_yun);
		EMOTION_CLASSIC_MAP.put("[月亮]", R.mipmap.emoji_yueliang);
		EMOTION_CLASSIC_MAP.put("[棒棒糖]", R.mipmap.emoji_bangbangtang);
		EMOTION_CLASSIC_MAP.put("[汽车]", R.mipmap.emoji_qiche);
		EMOTION_CLASSIC_MAP.put("[沙发]", R.mipmap.emoji_shafa);
		EMOTION_CLASSIC_MAP.put("[流汗]", R.mipmap.emoji_liuhan);
		EMOTION_CLASSIC_MAP.put("[流泪]", R.mipmap.emoji_liulei);
		EMOTION_CLASSIC_MAP.put("[激动]", R.mipmap.emoji_jidong);
		EMOTION_CLASSIC_MAP.put("[灯泡]", R.mipmap.emoji_dengpao);
		EMOTION_CLASSIC_MAP.put("[炸弹]", R.mipmap.emoji_zhadan);
		EMOTION_CLASSIC_MAP.put("[熊猫]", R.mipmap.emoji_xionmao);
		EMOTION_CLASSIC_MAP.put("[爆筋]", R.mipmap.emoji_baojin);
		EMOTION_CLASSIC_MAP.put("[爱你]", R.mipmap.emoji_aini);
		EMOTION_CLASSIC_MAP.put("[爱心]", R.mipmap.emoji_aixin);
		EMOTION_CLASSIC_MAP.put("[爱情]", R.mipmap.emoji_aiqing);
		EMOTION_CLASSIC_MAP.put("[猪头]", R.mipmap.emoji_zhutou);
		EMOTION_CLASSIC_MAP.put("[猫咪]", R.mipmap.emoji_maomi);
		EMOTION_CLASSIC_MAP.put("[献吻]", R.mipmap.emoji_xianwen);
		EMOTION_CLASSIC_MAP.put("[玫瑰]", R.mipmap.emoji_meigui);
		EMOTION_CLASSIC_MAP.put("[瓢虫]", R.mipmap.emoji_piaochong);
		EMOTION_CLASSIC_MAP.put("[疑问]", R.mipmap.emoji_yiwen);
		EMOTION_CLASSIC_MAP.put("[白眼]", R.mipmap.emoji_baiyan);
		EMOTION_CLASSIC_MAP.put("[皮球]", R.mipmap.emoji_piqiu);
		EMOTION_CLASSIC_MAP.put("[睡觉]", R.mipmap.emoji_shuijiao);
		EMOTION_CLASSIC_MAP.put("[磕头]", R.mipmap.emoji_ketou);
		EMOTION_CLASSIC_MAP.put("[示爱]", R.mipmap.emoji_shiai);
		EMOTION_CLASSIC_MAP.put("[礼品袋]", R.mipmap.emoji_lipindai);
		EMOTION_CLASSIC_MAP.put("[礼物]", R.mipmap.emoji_liwu);
		EMOTION_CLASSIC_MAP.put("[篮球]", R.mipmap.emoji_lanqiu);
		EMOTION_CLASSIC_MAP.put("[米饭]", R.mipmap.emoji_mifan);
		EMOTION_CLASSIC_MAP.put("[糗大了]", R.mipmap.emoji_qiudal);
		EMOTION_CLASSIC_MAP.put("[红双喜]", R.mipmap.emoji_hongshuangxi);
		EMOTION_CLASSIC_MAP.put("[红灯笼]", R.mipmap.emoji_hongdenglong);
		EMOTION_CLASSIC_MAP.put("[纸巾]", R.mipmap.emoji_zhijin);
		EMOTION_CLASSIC_MAP.put("[胜利]", R.mipmap.emoji_shengli);
		EMOTION_CLASSIC_MAP.put("[色]", R.mipmap.emoji_se);
		EMOTION_CLASSIC_MAP.put("[药]", R.mipmap.emoji_yao);
		EMOTION_CLASSIC_MAP.put("[菜刀]", R.mipmap.emoji_caidao);
		EMOTION_CLASSIC_MAP.put("[蛋糕]", R.mipmap.emoji_dangao);
		EMOTION_CLASSIC_MAP.put("[蜡烛]", R.mipmap.emoji_lazhu);
		EMOTION_CLASSIC_MAP.put("[街舞]", R.mipmap.emoji_jiewu);
		EMOTION_CLASSIC_MAP.put("[衰]", R.mipmap.emoji_shuai);
		EMOTION_CLASSIC_MAP.put("[西瓜]", R.mipmap.emoji_xigua);
		EMOTION_CLASSIC_MAP.put("[调皮]", R.mipmap.emoji_tiaopi);
		EMOTION_CLASSIC_MAP.put("[象棋]", R.mipmap.emoji_xiangqi);
		EMOTION_CLASSIC_MAP.put("[跳绳]", R.mipmap.emoji_tiaosheng);
		EMOTION_CLASSIC_MAP.put("[跳跳]", R.mipmap.emoji_tiaotiao);
		EMOTION_CLASSIC_MAP.put("[车厢]", R.mipmap.emoji_chexiang);
		EMOTION_CLASSIC_MAP.put("[转圈]", R.mipmap.emoji_zhuanquan);
		EMOTION_CLASSIC_MAP.put("[鄙视]", R.mipmap.emoji_bishi);
		EMOTION_CLASSIC_MAP.put("[酷]", R.mipmap.emoji_ku);
		EMOTION_CLASSIC_MAP.put("[钞票]", R.mipmap.emoji_chaopiao);
		EMOTION_CLASSIC_MAP.put("[钻戒]", R.mipmap.emoji_zuanjie);
		EMOTION_CLASSIC_MAP.put("[闪电]", R.mipmap.emoji_shandian);
		EMOTION_CLASSIC_MAP.put("[闭嘴]", R.mipmap.emoji_bizui);
		EMOTION_CLASSIC_MAP.put("[闹钟]", R.mipmap.emoji_naozhong);
		EMOTION_CLASSIC_MAP.put("[阴险]", R.mipmap.emoji_yinxian);
		EMOTION_CLASSIC_MAP.put("[难过]", R.mipmap.emoji_nanguo);
		EMOTION_CLASSIC_MAP.put("[雨伞]", R.mipmap.emoji_yusan);
		EMOTION_CLASSIC_MAP.put("[青蛙]", R.mipmap.emoji_qingwa);
		EMOTION_CLASSIC_MAP.put("[面条]", R.mipmap.emoji_miantiao);
		EMOTION_CLASSIC_MAP.put("[鞭炮]", R.mipmap.emoji_bianpao);
		EMOTION_CLASSIC_MAP.put("[风车]", R.mipmap.emoji_fengche);
		EMOTION_CLASSIC_MAP.put("[飞吻]", R.mipmap.emoji_feiwen);
		EMOTION_CLASSIC_MAP.put("[飞机]", R.mipmap.emoji_feiji);
		EMOTION_CLASSIC_MAP.put("[饥饿]", R.mipmap.emoji_jie);
		EMOTION_CLASSIC_MAP.put("[香蕉]", R.mipmap.emoji_xiangjiao);
		EMOTION_CLASSIC_MAP.put("[骷髅]", R.mipmap.emoji_kulou);
		EMOTION_CLASSIC_MAP.put("[麦克风]", R.mipmap.emoji_maikefeng);
		EMOTION_CLASSIC_MAP.put("[麻将]", R.mipmap.emoji_majiang);
		EMOTION_CLASSIC_MAP.put("[鼓掌]", R.mipmap.emoji_guzhang);
		EMOTION_CLASSIC_MAP.put("[龇牙]", R.mipmap.emoji_ziya);
	}

	/**
	 * 根据名称获取当前表情图标R值
	 * @param EmotionType 表情类型标志符
	 * @param imgName 名称
	 * @return
	 */
	public static int getImgByName(int EmotionType,String imgName) {
		Integer integer=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				integer = EMOTION_CLASSIC_MAP.get(imgName);
				break;
			default:
				LogUtil.log("the emojiMap is null!! Handle Yourself ");
				break;
		}
		return integer == null ? -1 : integer;
	}

	/**
	 * 根据类型获取表情数据
	 * @param EmotionType
	 * @return
	 */
	public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
		ArrayMap EmojiMap=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				EmojiMap=EMOTION_CLASSIC_MAP;
				break;
			default:
				EmojiMap=EMPTY_MAP;
				break;
		}
		return EmojiMap;
	}
}
