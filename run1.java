package run1;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.Math;



public class run1{
    //跑道的四个角的经纬度坐标
    final double[][] way={
            {0，0},
            {0，0},
            {0，0},
            {0，0}
    };


    double x,y;
    int times;//循环次数，一圈分为80个坐标点

    public run1(){
        this.x=way[0][0];
        this.y=way[0][1];
        this.times=0;
    }

    //计算下一个点坐标，将跑道的一圈分为80个点
    public void setNextXY(){
        int n=times%20,m=times/20;//第n个点
        int k=(m+1)%4;
        //加入随机数模拟跑步
        this.x=(way[k][0]-way[m][0])/20.0*(double)n+way[m][0]+Math.random()/10000;
        this.y=(way[k][1]-way[m][1])/20.0*(double)n+way[m][1]+Math.random()/10000;
        times++;
        if(times==80) times=0;
    }

    //将坐标字符串写入到剪切版
    public void setStr(double x,double y){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪切板
        String str;//设置字符串
        str= String.format("%.6f",x) +","+ String.format("%.6f",y);
        StringSelection selection = new StringSelection(str);//构建String数据类型
        clipboard.setContents(selection, null);//添加文本到系统剪切板
        System.out.println(str);
    }

    //先点击输入框再按键 ctrl+A 再ctrl+V，用新坐标覆盖原来的坐标；
    public void enter() throws AWTException, InterruptedException {
        Robot ro=new Robot();
        ro.mousePress(InputEvent.BUTTON2_MASK);
        Thread.sleep(100);
        ro.mouseRelease(InputEvent.BUTTON2_MASK);
        Thread.sleep(100);


        ro.keyPress(KeyEvent.VK_CONTROL);
        ro.keyPress(KeyEvent.VK_A);
        Thread.sleep(100);
        ro.keyRelease(KeyEvent.VK_A);
        ro.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(100);


        ro.keyPress(KeyEvent.VK_CONTROL);
        ro.keyPress(KeyEvent.VK_V);
        Thread.sleep(100);
        ro.keyRelease(KeyEvent.VK_V);
        ro.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(100);

        ro.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(100);
        ro.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void main(String[] args) throws AWTException, InterruptedException {
        run1 r = new run1();
        Robot ro = new Robot();
        ro.delay(10000);//准备时间
        int i=350;
        while (i-->0) {
            r.setNextXY();
            r.setStr(r.x, r.y);
            r.enter();
            ro.delay(500);//速度
        }
    }
}
