package cn.hd.test.io;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class J8ReadTest {
    public static void main(String[] args) throws Exception{
        List<String> s = Files.readAllLines(Paths.get("test/a.txt"));
        //System.out.println(s.stream().map(x->Integer.valueOf(x)).reduce(Integer::sum).get());
        List<String> list = new ArrayList<>();
        list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");
        
        System.out.println(String.join("|", list.subList(0, 2)));
        System.out.println(String.join("|", list.subList(2, 5)));

        System.out.println(String.join("|", s.subList(0, 100)));
        System.out.println(String.join("|", s.subList(100, 200)));
        System.out.println(String.join("|", s.subList(200, s.size())));

        String x= "上头了, doge, 很好很强大, 还不错啊, 创意不错, 新粉进来看看, 顶顶顶, 太真实了, 专业, 好家伙, 好玩, 能快点吗, 棒, 有点意思啊, 可以的, 戏精本精, 喵喵喵？, 转发, 太草了, 萌新瑟瑟发抖, 支持, 我了个去, 服了you, 配音哦, 已转, [doge], 莫名带感怎么回事, 蛤蛤, 好奇后续进展, 哈哈，沙发, 草草草, 勇闯无人区, good, 过于生草, 已坐稳，请开车, (/▽＼), 微妙, 意外的不错, 推荐进来的，看看, 说得好, 鸡冻了, 闪电五连鞭, 狂喜, 炸鱼, 芜湖, 每日撒糖, 这个好玩, 必须的, 牛啤, 靠谱, 好货, 人傻了, flow, 催更, 下面继续, 魔性, 路过, 挺好看, 新鲜的, 还不够还不够, 你们好坏, 泪目, 已转发, 一脸认真, 冲冲冲, 坐等更新, 好帅, 可可爱爱, 走起, 点赞, 有个性, 生草, 神仙, 赞, 敢不敢多搞点, 针不戳, 厉害炸了, 支持你哦, 哈哈又要搞事情, hiahiahia, 秀儿, 23333333, 啥啥啥, 可以分享吗, 打酱油来了, 继续更新呀, 我觉得可以, 后面怎么样了, 爷爱了, 加油啊, 感觉还行, 赶脚要火, 年轻人，劝你耗子尾汁, 还没过瘾呀, 有点意思, 斯国以, 下饭, 画面不错, 高能啊, 人才, 莫名上头怎么回事, 哈哈，年轻人耗子尾汁, 精彩！, 老铁666, 下次一定, 继续继续, 太妙了, 233, 还不错哦, 太短啦，催更, 我来也, 阔以, 已阅, 爷青回, 搞起来~, 好活, 还有吗, 转发了, 加油, 时间太短了, 可还行, 继续努力, 后面还有吗？, 有创意, 水土不服就服你, 真实, 牛啤牛啤, 乌拉~, (￣▽￣), 爽, 纳尼, 莫名带感, 更新了吗, 编的不赖, \\\\\\\\^o^/, 蛮不错, 妙哇种子, 我来组成头部, 太短了啦, 矮油，不错哦, 毫无违和感, 角色不赖, 硬核, 演的不错, 顶, 趁热, orz, 飘～, 好物, 直呼内行, 催催催, 火钳刘明, 来啦, 就服你, awsl, 可以的可以的, hhh, gkdgkd, 哎呦，不错哦, 舒服了, 放开他，让我来, 妙啊, 欢乐, 无情哈拉少, 哈哈哈哈, 妥妥哒, 我有个大胆的想法, 呱唧呱唧, 继续呀, 消灭零回复, 精彩, 酷, 这个必须回复, 不错, 嚯嚯嚯, 更新安排一下, 全体起立, 搬凳子看戏, 人才啊, nb, 我看好你, 有内味儿了, 拜～, 搞的不错, 有点内味儿了, 桀桀桀, 帅, 不错唉, 斯巴拉西, 耗子尾汁, 关注了, 乌拉, 厉害了, 牛逼plus, 卖瓜子看戏, 秀啊, 关注, 嘻嘻, hhhhhh, 阔以阔以, 有趣, 妈耶, 爱了爱了, 插眼, 路过刘明, 占坑, 老司机了, 秒, 妙不可言, 编的不错, 不赖嘛, 11111, cool, 要开车？, 沙发, 好评, 逮虾户！, www, 支持了, 热乎的, 哈哈哈, 我可以, gkd, 优秀, 哈哈, 就很OK, 不错，不错, 真有你的, 有内涵, 太短啦, 到位, 大威天龙, 能再长点吗, 哈哈，有毒, 给力, 哈哈认真的吗, 有后续吗？, 搞快点呀, 中意你啊, 妙妙妙, 阅, 哈哈哈哈哈, 哟西, 该来的还是来了, 哈哈有内味了, 满血复活, 鼓掌, 分享进来瞅瞅, 6啊, 哦哈, 飘过, 奇妙, 高能, 后面呢, 666, 沙发!, 不要停下来啊";
        System.out.println(x.split(", ").length);
        String [] ar = x.split(", ");
        for (int i = 0; i < ar.length; i++) {
            System.out.println(ar[i]);
        }
    }
}
