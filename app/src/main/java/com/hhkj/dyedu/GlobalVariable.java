package com.hhkj.dyedu;

/**
 * Created by zangyi_shuai_ge on 2017/9/28
 * 全局静态变量接口
 */

public interface GlobalVariable {


    //BaseActivity 封装的广播
    String BROADCAST_TOKEN_ERROR = "token_error";//token失效广播
    String BROADCAST_SERVER_EXCEPTION = "server_exception";//服务器异常广播


    //阿里云播放器配置参数
    String ALiAcId="LTAI6e1l2xmvIUaK";
    String ALiAkSceret="FuUEEoP1qz7dfnYY8JmJ76B7K5wyTF";
    String ALiSecurityToken="bn722djkxktpnbqihf4ywsmgxwqcptu159hmpe2r6e7ntlmv9z7nksnqf3fpav1y";


    //验证码获取时间间隔配置
    int CountTime=1000*20;

      int SECOND =1000;

    //图片测试数据
    String TEST_IMAGE_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508233421698&di=57080b62912324f4ac62c8de2c26811c&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fcefc1e178a82b90108814ef27a8da9773812efc7.jpg";

    String TEST_IMAGE_URL1="https://img.mukewang.com/szimg/5a405cbc000124ca05400300-360-202.jpg";
    String TEST_IMAGE_URL2="https://img.mukewang.com/szimg/59eeb21c00012eb205400300-360-202.jpg";
    String TEST_IMAGE_URL3="https://img.mukewang.com/szimg/583e42fb0001e04f05400300-360-202.jpg";
    String TEST_IMAGE_URL4="https://img.mukewang.com/szimg/5a308c9400011c1305400300-360-202.jpg";
    String TEST_IMAGE_URL5="https://img.mukewang.com/szimg/5a17ef670001292c05400300-360-202.jpg";

    String TEST_IMAGE_URL6="https://img.mukewang.com/szimg/5a123d7e00011fa705400300-360-202.jpg";
    String TEST_IMAGE_URL7="https://img.mukewang.com/szimg/59fc347400016ad405400300-360-202.jpg";
    String TEST_IMAGE_URL8="https://img.mukewang.com/szimg/59f684ee0001dbfa05400300-360-202.jpg";
    String TEST_IMAGE_URL9="https://img.mukewang.com/szimg/59dada7100016d9405400300-360-202.jpg";
    String bookId="";

    //微信AppId
    String APP_ID="wx2c8129d868852f33";



}
