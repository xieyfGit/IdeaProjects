package jdk8.design.adapter;

/**
 * 适配器工作场景:
 *  有一个面包店招聘：招聘启事中指明：仅限女性。
 *   但有个男性求职者啊，他力道好、技术专业、经验老道。这可把他给难倒了。
 *   于是啊，边上的人就出了个主意：“兄弟，你女扮男装能接受不？”
 *   男性说：“能啊，我老婆快生孩子了，奶粉钱都快没了。”就这样，男子女扮男装就混进去了。
 *   老板一看，“呀，你这是干啥呀？”。男子好一阵解释，老板才道：
 *   “既然职位要求的你都能做，这打扮得也还像那么回事。行吧。不过啊，你出入还是得女扮男装哈。”
 *
 */
public class AdapterMain {

    private Target target;

    public AdapterMain(Target target) {
        this.target = target;
    }

    public void consume() {
        target.consume();
    }

    public static void main(String[] args) {
        Target target = new Adapter();
        AdapterMain adapterMain = new AdapterMain(target);

        adapterMain.consume();
    }
}
